const user = requireAuth();
let activeRentalId = null;

renderNav('scooters.html');

async function loadScooters() {
    const list = document.getElementById('scooters-list');
    list.innerHTML = '<p>Загрузка...</p>';
    try {
        const scooters = await apiRequest('GET', '/scooters/available');
        if (!scooters.length) {
            list.innerHTML = '<p>Нет доступных самокатов</p>';
            return;
        }
        list.innerHTML = '';
        scooters.forEach(s => {
            const card = document.createElement('div');
            card.className = 'card';
            card.innerHTML = `
                <h3>🛴 ${s.serialNumber}</h3>
                <p>Модель: ${s.model || '—'}</p>
                <p>Батарея: ${s.batteryLevel ?? '—'}%</p>
                <p>Локация: ${s.latitude?.toFixed(4) ?? '—'}, ${s.longitude?.toFixed(4) ?? '—'}</p>
                <button onclick="startRental('${s.id}')">Арендовать</button>
            `;
            list.appendChild(card);
        });
    } catch (e) {
        list.innerHTML = `<p class="error">Ошибка: ${e.message}</p>`;
    }
}

async function loadActiveRental() {
    const block = document.getElementById('active-block');
    const info = document.getElementById('active-info');
    try {
        const r = await apiRequest('GET', `/rentals/active/${user.id}`);
        activeRentalId = r.rentalId;
        block.classList.remove('hidden');
        info.innerHTML = `
            <p><b>Самокат:</b> ${r.scooterSerial}</p>
            <p><b>Начало:</b> ${new Date(r.startTime).toLocaleString()}</p>
            <p><b>Статус:</b> ${r.status}</p>
        `;
    } catch (e) {
        block.classList.add('hidden');
        activeRentalId = null;
    }
}

async function startRental(scooterId) {
    try {
        const rental = await apiRequest('POST', '/rentals/start', {
            userId: user.id,
            scooterId: scooterId
        });
        alert(`Аренда начата! Самокат: ${rental.scooterSerial}`);
        location.reload();
    } catch (e) {
        alert('Ошибка: ' + e.message);
    }
}

async function endRental() {
    if (!activeRentalId) return;
    try {
        const r = await apiRequest('POST', '/rentals/end', { rentalId: activeRentalId });
        alert(`Аренда завершена!\nСтоимость: ${r.totalCost} ₽\nСтатус: ${r.status}`);
        location.reload();
    } catch (e) {
        alert('Ошибка: ' + e.message);
    }
}

loadScooters();
loadActiveRental();