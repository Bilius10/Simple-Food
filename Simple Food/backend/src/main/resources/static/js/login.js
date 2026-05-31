(function(){
  const form = document.getElementById('login-form');
  const errorBox = document.getElementById('error');
  const successBox = document.getElementById('success');

  function showError(msg){
    errorBox.hidden = false;
    errorBox.textContent = msg;
  }

  function clearError(){
    errorBox.hidden = true;
    errorBox.textContent = '';
  }

  function showSuccess(msg){
    if(!successBox) return;
    successBox.hidden = false;
    successBox.textContent = msg;
  }

  // show success if redirected after registration
  try{
    const params = new URLSearchParams(window.location.search);
    if(params.get('registered') === '1'){
      showSuccess('Registro efetuado com sucesso. Faça login.');
    }
  }catch(_){ }

  function setCookie(name, value, expiresAtIso) {
    let cookie = name + '=' + encodeURIComponent(value) + '; Path=/; SameSite=Lax';
    // If site served over HTTPS in production, consider setting Secure flag.
    cookie += ';';
    if (expiresAtIso) {
      try {
        const expDate = new Date(expiresAtIso);
        if (!isNaN(expDate.getTime())) {
          cookie += ' Expires=' + expDate.toUTCString() + ';';
        }
      } catch (e) {
        // ignore
      }
    }
    document.cookie = cookie;
  }

  form.addEventListener('submit', async function(e){
    e.preventDefault();
    clearError();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    if(!email || !password){
      showError('Preencha e-mail e senha.');
      return;
    }

    try{
      const res = await fetch('/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email, password })
      });

      if(!res.ok){
        // tenta extrair mensagem do backend
        let msg = 'Falha ao autenticar. Verifique suas credenciais.';
        try{ const body = await res.json(); if(body && body.message) msg = body.message; }catch(_){ }
        showError(msg);
        return;
      }

      const data = await res.json();
      // salva token e redireciona
      localStorage.setItem('sf_token', data.token);
      if(data.expiresAt) localStorage.setItem('sf_token_expires', data.expiresAt);

      // also store token as cookie so server-side filter can read it on page navigation
      // expiresAt is expected to be an ISO-8601 string (from server) or undefined
      setCookie('sf_token', data.token, data.expiresAt);

      // redireciona para dashboard (ajuste conforme sua rota)
      window.location.href = '/dashboard';

    }catch(err){
      console.error(err);
      showError('Erro de rede ao tentar efetuar login.');
    }
  });
})();
