const user = requireAuth();
renderNav('profile.html');

async function loadProfile() {
    try {
        const fresh = await apiRequest('GET', `/users/${user.id}`);
        saveUser(fresh);
        document.getElementById('profile-info').innerHTML = `
            <p><b>Логин:</b> ${fresh.login}</p>
            <p><b>ФИО:</b> ${fresh.fullName}</p>
            <p><b>Email:</b> ${fresh.email}</p>
            <p><b>Баланс:</b> <span style="color:#2b6cb0;font-weight:bold">${fresh.balance} ₽</span></p>
        `;
    } catch (e) {
        alert('Ошибка: ' + e.message);
    }
}

async function topUp() {
    const amount = parseFloat(document.getElementById('topup-amount').value);
    if (!amount || amount <= 0) {
        alert('Введите положительную сумму');
        return;
    }
    try {
        await apiRequest('POST', `/users/${user.id}/topup`, { amount });
        alert('Баланс пополнен на ' + amount + ' ₽');
        loadProfile();
    } catch (e) {
        alert('Ошибка: ' + e.message);
    }
}

loadProfile();