(function () {
  const page = document.querySelector('[data-page="dashboard"]');

  if (!page) {
    return;
  }

  const token = localStorage.getItem('sf_token');
  const userId = localStorage.getItem('sf_userId');

  if (!token || !userId) {
    window.location.href = '/login';
    return;
  }

  const headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + token
  };

  const dailyDateInput = document.getElementById('daily-date');
  const rangeStartInput = document.getElementById('range-start');
  const rangeEndInput = document.getElementById('range-end');
  const dailyCountEl = document.getElementById('daily-count');
  const metricProteinEl = document.getElementById('metric-protein');
  const metricCarbEl = document.getElementById('metric-carb');
  const metricFatEl = document.getElementById('metric-fat');
  const dashboardTitle = document.getElementById('dashboard-title');
  const dashboardSubtitle = document.getElementById('dashboard-subtitle');
  const dailySummaryEl = document.getElementById('daily-summary');
  const dailyTableContainer = document.getElementById('daily-table-container');
  const topFoodsTableEl = document.getElementById('top-foods-table');
  const macrosSummaryEl = document.getElementById('macros-summary');
  const toastEl = document.getElementById('toast');

  let toastTimer = null;

  function isoDateTimeStart(dateStr) {
    return dateStr + 'T00:00:00';
  }

  function isoDateTimeEnd(dateStr) {
    return dateStr + 'T23:59:59';
  }

  async function fetchWithAuth(url) {
    const response = await fetch(url, { headers: headers });

    if (!response.ok) {
      const message = await response.text();
      throw new Error('Falha na requisicao: ' + response.status + ' ' + message);
    }

    return response.json();
  }

  function formatNumber(value, suffix) {
    if (value === null || value === undefined || Number.isNaN(Number(value))) {
      return '--';
    }

    const formatted = Number(value).toLocaleString('pt-BR', {
      maximumFractionDigits: 1
    });

    return suffix ? formatted + ' ' + suffix : formatted;
  }

  function formatDate(value) {
    try {
      const date = new Date(value);
      if (Number.isNaN(date.getTime())) {
        return value;
      }

      return date.toLocaleString('pt-BR', {
        dateStyle: 'short',
        timeStyle: 'short'
      });
    } catch (_error) {
      return value;
    }
  }

  function escapeHtml(value) {
    return String(value ?? '')
      .replaceAll('&', '&amp;')
      .replaceAll('<', '&lt;')
      .replaceAll('>', '&gt;')
      .replaceAll('"', '&quot;')
      .replaceAll("'", '&#39;');
  }

  function showToast(message) {
    toastEl.textContent = message;
    toastEl.hidden = false;
    toastEl.classList.add('visible');

    window.clearTimeout(toastTimer);
    toastTimer = window.setTimeout(function () {
      toastEl.classList.remove('visible');
      window.setTimeout(function () {
        toastEl.hidden = true;
      }, 200);
    }, 2200);
  }

  function renderEmptyState(container, title, message, actionLabel, actionName) {
    const actionMarkup = actionLabel && actionName
      ? '<button type="button" class="btn-secondary" data-action="' + escapeHtml(actionName) + '">' + escapeHtml(actionLabel) + '</button>'
      : '';

    container.innerHTML =
      '<div class="empty-state">' +
        '<div>' +
          '<strong>' + escapeHtml(title) + '</strong>' +
          '<p>' + escapeHtml(message) + '</p>' +
          actionMarkup +
        '</div>' +
      '</div>';
  }

  function updateMetricCards(protein, carb, fat) {
    metricProteinEl.textContent = formatNumber(protein, 'g');
    metricCarbEl.textContent = formatNumber(carb, 'g');
    metricFatEl.textContent = formatNumber(fat, 'g');
  }

  function renderDailySummary(items, totals, dateStr) {
    dashboardTitle.textContent = 'Resumo do dia ' + dateStr.split('-').reverse().join('/');
    dashboardSubtitle.textContent = 'Visao rapida do consumo registrado e dos macronutrientes acumulados.';

    if (!items.length) {
      dailySummaryEl.innerHTML =
        '<strong>Nenhum registro encontrado</strong>' +
        '<div class="summary-meta">' +
          '<span>Voce ainda nao registrou refeicoes nesse dia.</span>' +
          '<span>Use outro dia ou adicione novos consumos pelo fluxo principal.</span>' +
        '</div>';
      return;
    }

    dailySummaryEl.innerHTML =
      '<strong>' + formatNumber(totals.calories, 'kcal') + ' no total</strong>' +
      '<div class="summary-meta">' +
        '<span>Proteina: ' + formatNumber(totals.protein, 'g') + '</span>' +
        '<span>Carboidratos: ' + formatNumber(totals.carb, 'g') + '</span>' +
        '<span>Gordura: ' + formatNumber(totals.fat, 'g') + '</span>' +
      '</div>';
  }

  function renderDailyEntries(items) {
    if (!items.length) {
      renderEmptyState(
        dailyTableContainer,
        'Voce ainda nao registrou refeicoes hoje.',
        'Escolha outra data ou atualize os dados para iniciar seu historico.',
        'Atualizar dados',
        'refresh-all'
      );
      return;
    }

    const html = items.map(function (item, index) {
      return (
        '<article class="entry-item">' +
          '<div class="entry-item__header">' +
            '<div>' +
              '<strong>' + escapeHtml(item.descricao || 'Alimento sem descricao') + '</strong>' +
              '<span>' + escapeHtml(item.categoria || 'Categoria nao informada') + '</span>' +
            '</div>' +
            '<span class="pill">#' + escapeHtml(item.numeroDoAlimento ?? index + 1) + '</span>' +
          '</div>' +
          '<div class="entry-meta">' +
            '<span>Quantidade: ' + formatNumber(item.quantidadeConsumida) + '</span>' +
            '<span>Calorias: ' + formatNumber(item.calorias, 'kcal') + '</span>' +
            '<span>Proteina: ' + formatNumber(item.proteina, 'g') + '</span>' +
            '<span>Carboidratos: ' + formatNumber(item.carboidrato, 'g') + '</span>' +
            '<span>Gordura: ' + formatNumber(item.gordura, 'g') + '</span>' +
            '<span>' + escapeHtml(formatDate(item.dataConsumo)) + '</span>' +
          '</div>' +
        '</article>'
      );
    }).join('');

    dailyTableContainer.innerHTML = '<div class="entry-list">' + html + '</div>';
  }

  function renderTopFoods(items) {
    if (!items.length) {
      renderEmptyState(
        topFoodsTableEl,
        'Nenhum alimento recorrente no periodo.',
        'Aumente o intervalo para comparar os alimentos mais registrados.',
        'Atualizar periodo',
        'load-range'
      );
      return;
    }

    const peakCalories = Math.max.apply(null, items.map(function (item) {
      return Number(item.caloriasTotal || 0);
    }));

    const html = items.map(function (item) {
      const width = peakCalories > 0 ? Math.max((Number(item.caloriasTotal || 0) / peakCalories) * 100, 6) : 6;

      return (
        '<article class="food-item">' +
          '<div class="food-item__header">' +
            '<div>' +
              '<strong>' + escapeHtml(item.descricao || 'Alimento') + '</strong>' +
              '<span>' + escapeHtml(item.categoria || 'Categoria nao informada') + '</span>' +
            '</div>' +
            '<span class="pill">' + formatNumber(item.caloriasTotal, 'kcal') + '</span>' +
          '</div>' +
          '<div class="food-bar"><div class="food-bar__fill" style="width:' + width + '%"></div></div>' +
          '<div class="food-meta">' +
            '<span>Quantidade total: ' + formatNumber(item.quantidadeTotal) + '</span>' +
            '<span>Registros: ' + formatNumber(item.registrosCount) + '</span>' +
          '</div>' +
        '</article>'
      );
    }).join('');

    topFoodsTableEl.innerHTML = '<div class="food-list">' + html + '</div>';
  }

  function renderMacrosRange(items) {
    if (!items.length) {
      renderEmptyState(
        macrosSummaryEl,
        'Sem macronutrientes no periodo.',
        'Escolha um intervalo maior para visualizar a distribuicao nutricional.',
        'Atualizar periodo',
        'load-range'
      );
      return;
    }

    const totals = items.reduce(function (acc, item) {
      acc.protein += Number(item.proteina || 0);
      acc.carb += Number(item.carboidrato || 0);
      acc.fat += Number(item.gordura || 0);
      acc.calories += Number(item.caloriasTotais || 0);
      return acc;
    }, { protein: 0, carb: 0, fat: 0, calories: 0 });

    const maxMacro = Math.max(totals.protein, totals.carb, totals.fat, 1);

    const cards = [
      { label: 'Proteina', value: totals.protein, className: 'protein' },
      { label: 'Carboidratos', value: totals.carb, className: 'carb' },
      { label: 'Gordura', value: totals.fat, className: 'fat' }
    ].map(function (macro) {
      const width = Math.max((macro.value / maxMacro) * 100, 8);

      return (
        '<article class="macro-item">' +
          '<div class="macro-item__header">' +
            '<strong>' + macro.label + '</strong>' +
            '<span>' + formatNumber(macro.value, 'g') + '</span>' +
          '</div>' +
          '<div class="macro-bar"><div class="macro-bar__fill ' + macro.className + '" style="width:' + width + '%"></div></div>' +
        '</article>'
      );
    }).join('');

    macrosSummaryEl.innerHTML =
      '<div class="macro-list">' +
        cards +
        '<article class="macro-item">' +
          '<div class="macro-item__header">' +
            '<strong>Total energetico</strong>' +
            '<span>' + formatNumber(totals.calories, 'kcal') + '</span>' +
          '</div>' +
          '<p class="macro-meta">Periodo carregado com ' + formatNumber(items.length) + ' ponto(s) de consolidacao.</p>' +
        '</article>' +
      '</div>';
  }

  async function loadDaily(dateStr) {
    const start = isoDateTimeStart(dateStr);
    const end = isoDateTimeEnd(dateStr);

    try {
      const count = await fetchWithAuth('/registro_consumo/count?userId=' + userId + '&start=' + encodeURIComponent(start) + '&end=' + encodeURIComponent(end));
      const pageData = await fetchWithAuth('/registro_consumo?userId=' + userId + '&start=' + encodeURIComponent(start) + '&end=' + encodeURIComponent(end) + '&page=0&size=200');
      const macros = await fetchWithAuth('/registro_consumo/macros?userId=' + userId + '&start=' + encodeURIComponent(start) + '&end=' + encodeURIComponent(end));

      const entries = pageData && Array.isArray(pageData.content) ? pageData.content : [];
      const totals = macros.reduce(function (acc, item) {
        acc.protein += Number(item.proteina || 0);
        acc.carb += Number(item.carboidrato || 0);
        acc.fat += Number(item.gordura || 0);
        acc.calories += Number(item.caloriasTotais || 0);
        return acc;
      }, { protein: 0, carb: 0, fat: 0, calories: 0 });

      dailyCountEl.textContent = formatNumber(count);
      updateMetricCards(totals.protein, totals.carb, totals.fat);
      renderDailySummary(entries, totals, dateStr);
      renderDailyEntries(entries);
      showToast(entries.length ? 'Dia carregado com sucesso.' : 'Dia carregado sem registros.');
    } catch (error) {
      console.error(error);
      dailyCountEl.textContent = '--';
      updateMetricCards(null, null, null);
      renderEmptyState(
        dailyTableContainer,
        'Nao foi possivel carregar o dia selecionado.',
        'Revise sua sessao e tente novamente em alguns instantes.',
        'Tentar novamente',
        'load-daily'
      );
      dailySummaryEl.innerHTML =
        '<strong>Erro ao carregar resumo diario</strong>' +
        '<div class="summary-meta"><span>O backend nao retornou os dados esperados.</span></div>';
    }
  }

  async function loadRange(startDate, endDate) {
    const start = isoDateTimeStart(startDate);
    const end = isoDateTimeEnd(endDate);

    try {
      const results = await Promise.all([
        fetchWithAuth('/registro_consumo/top-foods?userId=' + userId + '&start=' + encodeURIComponent(start) + '&end=' + encodeURIComponent(end)),
        fetchWithAuth('/registro_consumo/macros?userId=' + userId + '&start=' + encodeURIComponent(start) + '&end=' + encodeURIComponent(end))
      ]);

      renderTopFoods(Array.isArray(results[0]) ? results[0] : []);
      renderMacrosRange(Array.isArray(results[1]) ? results[1] : []);
      showToast('Periodo atualizado.');
    } catch (error) {
      console.error(error);
      renderEmptyState(
        topFoodsTableEl,
        'Falha ao carregar top alimentos.',
        'Tente novamente para recompor o ranking do periodo.',
        'Atualizar periodo',
        'load-range'
      );
      renderEmptyState(
        macrosSummaryEl,
        'Falha ao carregar macronutrientes.',
        'O resumo do periodo sera exibido assim que a requisicao voltar a responder.',
        'Atualizar periodo',
        'load-range'
      );
    }
  }

  async function refreshAll() {
    if (!dailyDateInput.value || !rangeStartInput.value || !rangeEndInput.value) {
      showToast('Selecione as datas antes de atualizar.');
      return;
    }

    await Promise.all([
      loadDaily(dailyDateInput.value),
      loadRange(rangeStartInput.value, rangeEndInput.value)
    ]);
  }

  function onActionClick(event) {
    const trigger = event.target.closest('[data-action]');

    if (!trigger) {
      return;
    }

    const action = trigger.getAttribute('data-action');

    if (action === 'load-daily') {
      if (!dailyDateInput.value) {
        showToast('Selecione uma data valida.');
        return;
      }

      loadDaily(dailyDateInput.value);
      return;
    }

    if (action === 'load-range') {
      if (!rangeStartInput.value || !rangeEndInput.value) {
        showToast('Defina o periodo para continuar.');
        return;
      }

      loadRange(rangeStartInput.value, rangeEndInput.value);
      return;
    }

    if (action === 'refresh-all') {
      refreshAll();
    }
  }

  function setInitialDates() {
    const today = new Date();
    const todayIso = today.toISOString().split('T')[0];
    const weekAgo = new Date(today);
    weekAgo.setDate(today.getDate() - 7);

    dailyDateInput.value = todayIso;
    rangeEndInput.value = todayIso;
    rangeStartInput.value = weekAgo.toISOString().split('T')[0];
  }

  page.addEventListener('click', onActionClick);

  setInitialDates();
  refreshAll();
})();
