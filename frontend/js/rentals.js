const user = requireAuth();
renderNav('rentals.html');

async function loadHistory() {
    const tbody = document.querySelector('#rentals-table tbody');
    try {
        const page = await apiRequest('GET', `/rentals/history/${user.id}?page=0&size=50`);
        tbody.innerHTML = '';
        if (!page.content || !page.content.length) {
            tbody.innerHTML = '<tr><td colspan="5">Поездок пока нет</td></tr>';
            return;
        }
        page.content.forEach(r => {
            const tr = document.createElement('tr');
            tr.innerHTML = `
                <td>${r.scooterSerial || '—'}</td>
                <td>${r.startTime ? new Date(r.startTime).toLocaleString() : '—'}</td>
                <td>${r.endTime ? new Date(r.endTime).toLocaleString() : '—'}</td>
                <td>${r.totalCost != null ? r.totalCost + ' ₽' : '—'}</td>
                <td><span class="status status-${r.status?.toLowerCase()}">${r.status || '—'}</span></td>
            `;
            tbody.appendChild(tr);
        });
    } catch (e) {
        tbody.innerHTML = `<tr><td colspan="5" class="error">Ошибка: ${e.message}</td></tr>`;
    }
}

loadHistory();