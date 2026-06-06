(function () {
  const form = document.getElementById('registro-form');
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

  function showSuccess(message) {
    successBox.hidden = false;
    successBox.textContent = message;
    errorBox.hidden = true;
    errorBox.textContent = '';
  }

  function clearMessages() {
    errorBox.hidden = true;
    errorBox.textContent = '';
    successBox.hidden = true;
    successBox.textContent = '';
  }

  form.addEventListener('submit', async function (event) {
    event.preventDefault();
    clearMessages();

    const name = document.getElementById('name').value.trim();
    const email = document.getElementById('email').value.trim();
    const whatsapp = document.getElementById('whatsapp').value.trim();
    const password = document.getElementById('password').value;
    const passwordConfirm = document.getElementById('passwordConfirm').value;

    if (!name || !email || !whatsapp || !password || !passwordConfirm) {
      showError('Preencha todos os campos.');
      return;
    }

    if (password !== passwordConfirm) {
      showError('As senhas nao coincidem.');
      return;
    }

    try {
      const response = await fetch('/auth/register', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json'
        },
        body: JSON.stringify({
          name: name,
          email: email,
          whatsappNumber: whatsapp,
          password: password
        })
      });

      if (!response.ok) {
        let message = 'Falha ao registrar. Verifique os dados.';
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

      showSuccess('Registro efetuado com sucesso. Redirecionando para login...');
      window.setTimeout(function () {
        window.location.href = '/auth/login?registered=1';
      }, 1200);
    } catch (error) {
      console.error(error);
      showError('Erro de rede ao tentar registrar.');
    }
  });
})();
