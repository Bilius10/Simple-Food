(function () {
  const page = document.querySelector('[data-page="usuario-edit"]');
  if (!page) return;

  const token = localStorage.getItem('sf_token');
  const userIdFromStorage = localStorage.getItem('sf_userId');
  const toastEl = document.getElementById('toast');
  const successEl = document.getElementById('success');
  const errorEl = document.getElementById('error');

  if (!token) {
    window.location.href = '/login';
    return;
  }

  const headers = {
    'Content-Type': 'application/json',
    'Authorization': 'Bearer ' + token
  };

  const idFromPath = (function () {
    const m = window.location.pathname.match(/\/usuarios\/(\d+)\/editar/);
    return m ? m[1] : null;
  })();

  const userId = idFromPath || userIdFromStorage;

  if (!userId) {
    showError('Nao foi possivel determinar o usuario.');
    return;
  }

  const nomeInput = document.getElementById('nome');
  const emailInput = document.getElementById('email');
  const whatsappInput = document.getElementById('numeroWhatsapp');
  const metaInput = document.getElementById('metaCaloriasDiarias');
  const form = document.getElementById('user-form');
  const saveBtn = document.getElementById('save-btn');

  function showToast(message) {
    if (!toastEl) return;
    toastEl.textContent = message;
    toastEl.hidden = false;
    toastEl.classList.add('visible');
    setTimeout(() => {
      toastEl.classList.remove('visible');
      setTimeout(() => { toastEl.hidden = true; }, 200);
    }, 2200);
  }

  function showSuccess(message) {
    if (!successEl) return showToast(message);
    successEl.textContent = message;
    successEl.hidden = false;
    setTimeout(() => { successEl.hidden = true; }, 2200);
  }

  function showError(message) {
    if (!errorEl) return showToast(message);
    errorEl.textContent = message;
    errorEl.hidden = false;
    setTimeout(() => { errorEl.hidden = true; }, 4000);
  }

  async function loadUser() {
    try {
      const res = await fetch('/usuarios/' + encodeURIComponent(userId), { headers });
      if (!res.ok) {
        const txt = await res.text();
        throw new Error('Falha: ' + res.status + ' ' + txt);
      }

      const data = await res.json();
      nomeInput.value = data.nome || '';
      emailInput.value = data.email || '';
      whatsappInput.value = data.numeroWhatsapp || '';
      metaInput.value = data.metaCaloriasDiarias == null ? '' : data.metaCaloriasDiarias;

      showToast('Dados carregados.');
    } catch (err) {
      console.error(err);
      showError('Nao foi possivel carregar os dados do usuario.');
    }
  }

  async function saveUser(ev) {
    ev.preventDefault();
    saveBtn.disabled = true;

    const payload = {
      nome: nomeInput.value || null,
      email: emailInput.value || null,
      numeroWhatsapp: whatsappInput.value || null,
      metaCaloriasDiarias: metaInput.value ? Number(metaInput.value) : null
    };

    try {
      const res = await fetch('/usuarios/' + encodeURIComponent(userId), {
        method: 'PUT',
        headers: headers,
        body: JSON.stringify(payload)
      });

      if (!res.ok) {
        const txt = await res.text();
        throw new Error('Falha: ' + res.status + ' ' + txt);
      }

      const updated = await res.json();
      showSuccess('Perfil atualizado com sucesso.');
      // atualiza localStorage se necessário
      if (userIdFromStorage) {
        // opcional: atualizar campos locais
      }
    } catch (err) {
      console.error(err);
      showError('Falha ao atualizar o perfil.');
    } finally {
      saveBtn.disabled = false;
    }
  }

  form.addEventListener('submit', saveUser);
  loadUser();
})();

