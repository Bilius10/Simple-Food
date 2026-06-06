(function () {
  const form = document.getElementById('login-form');
  const errorBox = document.getElementById('error');
  const successBox = document.getElementById('success');

  if (!form || !errorBox || !successBox) {
    return;
  }

  function showError(message) {
    errorBox.hidden = false;
    errorBox.textContent = message;
    successBox.hidden = true;
    successBox.textContent = '';
  }

  function clearMessages() {
    errorBox.hidden = true;
    errorBox.textContent = '';
    successBox.hidden = true;
    successBox.textContent = '';
  }

  function showSuccess(message) {
    successBox.hidden = false;
    successBox.textContent = message;
  }

  try {
    const params = new URLSearchParams(window.location.search);
    if (params.get('registered') === '1') {
      showSuccess('Registro efetuado com sucesso. Faca login.');
    }
  } catch (_error) {
    // noop
  }

  function setCookie(name, value, expiresAtIso) {
    let cookie = name + '=' + encodeURIComponent(value) + '; Path=/; SameSite=Lax;';

    if (expiresAtIso) {
      try {
        const expirationDate = new Date(expiresAtIso);
        if (!Number.isNaN(expirationDate.getTime())) {
          cookie += ' Expires=' + expirationDate.toUTCString() + ';';
        }
      } catch (_error) {
        // noop
      }
    }

    document.cookie = cookie;
  }

  form.addEventListener('submit', async function (event) {
    event.preventDefault();
    clearMessages();

    const email = document.getElementById('email').value.trim();
    const password = document.getElementById('password').value;

    if (!email || !password) {
      showError('Preencha e-mail e senha.');
      return;
    }

    try {
      const response = await fetch('/auth/login', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({ email: email, password: password })
      });

      if (!response.ok) {
        let message = 'Falha ao autenticar. Verifique suas credenciais.';
        try {
          const body = await response.json();
          if (body && body.message) {
            message = body.message;
          }
        } catch (_error) {
          // noop
        }
        showError(message);
        return;
      }

      const data = await response.json();
      localStorage.setItem('sf_token', data.token);

      if (data.expiresAt) {
        localStorage.setItem('sf_token_expires', data.expiresAt);
      }

      if (data.userId) {
        localStorage.setItem('sf_userId', data.userId);
      }

      setCookie('sf_token', data.token, data.expiresAt);
      window.location.href = '/dashboard';
    } catch (error) {
      console.error(error);
      showError('Erro de rede ao tentar efetuar login.');
    }
  });
})();
