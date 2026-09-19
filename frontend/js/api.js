const API_BASE = 'http://localhost:8080/api';

function saveUser(user) {
    localStorage.setItem('user', JSON.stringify(user));
}

function getUser() {
    const u = localStorage.getItem('user');
    return u ? JSON.parse(u) : null;
}

function logout() {
    localStorage.removeItem('user');
    window.location.href = 'index.html';
}

function requireAuth() {
    const user = getUser();
    if (!user) {
        window.location.href = 'index.html';
        return null;
    }
    return user;
}

async function apiRequest(method, path, body = null) {
    const options = {
        method: method,
        headers: { 'Content-Type': 'application/json' }
    };
    if (body) options.body = JSON.stringify(body);

    const response = await fetch(API_BASE + path, options);
    const text = await response.text();
    let data = null;
    try { data = text ? JSON.parse(text) : null; }
    catch (e) { data = text; }

    if (!response.ok) {
        throw new Error(typeof data === 'string' ? data : JSON.stringify(data));
    }
    return data;
}

function renderNav(active) {
    const links = [
        { href: 'scooters.html', label: '🛴 Самокаты' },
        { href: 'profile.html',  label: '👤 Профиль' },
        { href: 'rentals.html',  label: '📜 История' }
    ];
    const nav = document.createElement('nav');
    links.forEach(l => {
        const a = document.createElement('a');
        a.href = l.href;
        a.textContent = l.label;
        if (l.href === active) a.classList.add('active');
        nav.appendChild(a);
    });
    const out = document.createElement('a');
    out.href = '#';
    out.textContent = '🚪 Выйти';
    out.onclick = (e) => { e.preventDefault(); logout(); };
    nav.appendChild(out);
    document.body.prepend(nav);
}