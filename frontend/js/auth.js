function showTab(tab) {
    document.querySelectorAll('.tab').forEach(t => t.classList.remove('active'));
    document.querySelectorAll('.form-block').forEach(f => f.classList.add('hidden'));

    if (tab === 'login') {
        document.getElementById('tab-login').classList.add('active');
        document.getElementById('login-form').classList.remove('hidden');
    } else {
        document.getElementById('tab-register').classList.add('active');
        document.getElementById('register-form').classList.remove('hidden');
    }
}

async function doLogin() {
    const login = document.getElementById('login-login').value.trim();
    const password = document.getElementById('login-password').value;
    const errEl = document.getElementById('login-error');
    errEl.textContent = '';

    if (!login || !password) { errEl.textContent = 'Заполните все поля'; return; }

    try {
        const user = await apiRequest('POST', '/users/login', { login, password });
        saveUser(user);
        window.location.href = 'scooters.html';
    } catch (e) {
        errEl.textContent = 'Ошибка: ' + e.message;
    }
}

async function doRegister() {
    const login = document.getElementById('reg-login').value.trim();
    const password = document.getElementById('reg-password').value;
    const fullName = document.getElementById('reg-fullname').value.trim();
    const phone = document.getElementById('reg-phone').value.trim();
    const email = document.getElementById('reg-email').value.trim();
    const errEl = document.getElementById('register-error');
    errEl.textContent = '';

    if (!login || !password || !fullName || !phone || !email) {
        errEl.textContent = 'Заполните все поля';
        return;
    }

    try {
        const user = await apiRequest('POST', '/users/register', {
            login, password, fullName, phone, email
        });
        saveUser(user);
        alert('Регистрация успешна! Пополните баланс в профиле.');
        window.location.href = 'profile.html';
    } catch (e) {
        errEl.textContent = 'Ошибка: ' + e.message;
    }
}

if (getUser()) {
    window.location.href = 'scooters.html';
}