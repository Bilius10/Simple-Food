(function(){
  const form = document.getElementById('registro-form');
  const errorBox = document.getElementById('error');
  const successBox = document.getElementById('success');

  function showError(msg){
    errorBox.hidden = false;
    errorBox.textContent = msg;
    successBox.hidden = true;
    successBox.textContent = '';
  }

  function showSuccess(msg){
    successBox.hidden = false;
    successBox.textContent = msg;
    errorBox.hidden = true;
    errorBox.textContent = '';
  }

  function clearMessages(){
    errorBox.hidden = true; errorBox.textContent = '';
    successBox.hidden = true; successBox.textContent = '';
  }

  form.addEventListener('submit', async function(e){
    e.preventDefault();
    clearMessages();

    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const whatsapp = document.getElementById('whatsapp').value.trim();
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('passwordConfirm').value;

    if(!name || !email || !whatsapp || !password || !passwordConfirm){
      showError('Preencha todos os campos.');
      return;
    }

    if(password !== passwordConfirm){
      showError('As senhas não coincidem.');
      return;
    }

    try{
      const res = await fetch('/auth/register', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify({ name, email, whatsappNumber: whatsapp, password })
      });

      if(!res.ok){
        let msg = 'Falha ao registrar. Verifique os dados.';
        try{ const b = await res.json(); if(b && b.message) msg = b.message; }catch(_){ }
        showError(msg);
        return;
      }

      // sucesso
      showSuccess('Registro efetuado com sucesso. Redirecionando para login...');
      setTimeout(() => { window.location.href = '/auth/login?registered=1'; }, 1200);

    }catch(err){
      console.error(err);
      showError('Erro de rede ao tentar registrar.');
    }
  });
})();

