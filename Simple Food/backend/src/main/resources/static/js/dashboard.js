(function(){
  const menu = document.getElementById('menu');
  const content = document.getElementById('content');

  function setActive(el){
    Array.from(menu.querySelectorAll('li')).forEach(li => li.classList.remove('active'));
    el.classList.add('active');
  }

  function showContent(key){
    if(key === 'dashboard'){
      content.innerHTML = '<h2>Bem-vindo ao Dashboard</h2><p class="muted">Visão geral do aplicativo aparecerá aqui.</p>';
    } else if(key === 'cadastrar'){
      content.innerHTML = '<h2>Cadastrar consumo</h2><p class="muted">Formulário para registrar consumo será implementado aqui. (demo sem redirecionamento)</p>';
    } else if(key === 'visualizar'){
      content.innerHTML = '<h2>Visualizar alimentos</h2><p class="muted">Lista de alimentos será exibida aqui.</p>';
    }
  }

  menu.addEventListener('click', function(e){
    const li = e.target.closest('li');
    if(!li) return;
    setActive(li);
    showContent(li.getAttribute('data-key'));
  });

})();