// Support API Calls if needed, but here direct in components
export async function loginUser({ username, password }) {
  const res = await fetch("https://8080-fbdedafcaedabcdd333220481ebaccebeedctwo.premiumproject.examly.io/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  });
  return res.json();
}

export async function fetchPlayers(params) {
  const url = new URL("https://8080-fbdedafcaedabcdd333220481ebaccebeedctwo.premiumproject.examly.io/players");
  Object.keys(params).forEach(k => url.searchParams.append(k, params[k]));
  const res = await fetch(url.toString());
  return res.json();
}

export async function addPlayer(player) {
  const res = await fetch("https://8080-fbdedafcaedabcdd333220481ebaccebeedctwo.premiumproject.examly.io/players", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(player)
  });
  return res.json();
}

export async function updatePlayer(id, player) {
  const res = await fetch(`https://8080-fbdedafcaedabcdd333220481ebaccebeedctwo.premiumproject.examly.io/players/${id}`, {
    method: "PUT",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify(player)
  });
  return res.json();
}

export async function deletePlayer(id) {
  return fetch(`https://8080-fbdedafcaedabcdd333220481ebaccebeedctwo.premiumproject.examly.io/players/${id}`, { method: "DELETE" });
}
