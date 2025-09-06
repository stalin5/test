import React, { useEffect, useState } from "react";
import '../App.css';

const ROLE_OPTIONS = [
  "Batsman",
  "Bowler",
  "All-rounder",
  "Wicketkeeper"
];

const EMPTY_PLAYER = { playerName: "", age: "", role: "Batsman", country: "" };

function PlayerTable() {
  const [players, setPlayers] = useState([]);
  const [form, setForm] = useState(EMPTY_PLAYER);
  const [editId, setEditId] = useState(null);
  const [error, setError] = useState("");
  const [loading, setLoading] = useState(false);

  // Table Params
  const [page, setPage] = useState(0);
  const [size, setSize] = useState(10);
  const [totalPages, setTotalPages] = useState(1);
  const [sortBy, setSortBy] = useState("playerId");
  const [roleFilter, setRoleFilter] = useState("");
  const [search, setSearch] = useState("");

  function fetchPlayers() {
    setLoading(true);
    let url = `https://8080-fbdedafcaedabcdd333220481ebaccebeedctwo.premiumproject.examly.io/players?page=${page}&size=${size}`;
    if (sortBy) url += `&sortBy=${sortBy}`;
    if (roleFilter) url += `&role=${encodeURIComponent(roleFilter)}`;
    if (search) url += `&search=${encodeURIComponent(search)}`;
    fetch(url)
      .then(r => r.json())
      .then(d => {
        setPlayers(d.content || []);
        setTotalPages(d.totalPages || 1);
      })
      .catch(() => setPlayers([]))
      .finally(() => setLoading(false));
  }

  useEffect(() => {
    fetchPlayers();
    // eslint-disable-next-line
  }, [page, size, sortBy, roleFilter, search]);

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  function validatePlayer(player) {
    if (!player.playerName) return "Player name is required";
    if (player.playerName.length < 2 || player.playerName.length > 50) return "Player name must be between 2 and 50 characters";
    if (!player.age) return "Age is required";
    if (+player.age < 18 || +player.age > 40) return "Age must be between 18 and 40";
    if (!player.role || !ROLE_OPTIONS.includes(player.role)) return "Role is invalid";
    if (!player.country) return "Country is required";
    if (player.country.length < 2 || player.country.length > 30) return "Country must be between 2 and 30 characters";
    return "";
  }

  const handleSubmit = (e) => {
    e.preventDefault();
    setError("");
    const v = validatePlayer(form);
    if (v) return setError(v);
    const method = editId ? "PUT" : "POST";
    const url = editId ? `https://8080-fbdedafcaedabcdd333220481ebaccebeedctwo.premiumproject.examly.io//players/${editId}` : "/players";
    fetch(url, {
      method,
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({ ...form, age: +form.age }),
    })
      .then(async (r) => {
        const d = await r.json();
        if (r.ok) {
          setForm(EMPTY_PLAYER);
          setEditId(null);
          fetchPlayers();
        } else setError(d.error || "Submission error");
      })
      .catch(() => setError("Network error"));
  };

  const handleEdit = (player) => {
    setForm({
      playerName: player.playerName,
      age: player.age,
      role: player.role,
      country: player.country
    });
    setEditId(player.playerId);
    setError("");
  };
  const handleDelete = (playerId) => {
    if (!window.confirm('Delete player?')) return;
    fetch(`/players/${playerId}`, { method: 'DELETE' })
      .then(r => {
        if (r.status === 204) fetchPlayers();
        else setError("Delete failed");
      })
      .catch(() => setError("Delete failed"));
  };

  return (
    <div style={{ maxWidth: 720, margin: "2rem auto" }}>
      <h2>Cricket Team Players</h2>
      <div className="form-container" style={{ marginTop: "1.5rem", marginBottom: 24 }}>
        <form onSubmit={handleSubmit}>
          <div style={{ display: "flex", gap: 10, flexWrap: "wrap" }}>
            <div style={{ flex: 1 }}>
              <label htmlFor="playerName">Player Name</label>
              <input id="playerName" name="playerName" value={form.playerName} onChange={handleChange} />
            </div>
            <div style={{ width: 100 }}>
              <label htmlFor="age">Age</label>
              <input id="age" name="age" type="number" value={form.age} min={18} max={40} onChange={handleChange} />
            </div>
            <div style={{ minWidth: 150 }}>
              <label htmlFor="role">Role</label>
              <select id="role" name="role" value={form.role} onChange={handleChange}>
                {ROLE_OPTIONS.map(r => <option key={r}>{r}</option>)}
              </select>
            </div>
            <div style={{ flex: 1 }}>
              <label htmlFor="country">Country</label>
              <input id="country" name="country" value={form.country} onChange={handleChange} />
            </div>
            <div style={{ alignSelf: "flex-end" }}>
              <button className="btn-primary" type="submit">{editId ? "Update" : "Add"} Player</button>
              {editId && <button className="btn-secondary" type="button" style={{ marginLeft: 8 }} onClick={() => { setForm(EMPTY_PLAYER); setEditId(null); }}>Cancel</button>}
            </div>
          </div>
          {error && <div className="error" style={{ marginTop: 12 }}>{error}</div>}
        </form>
      </div>
      <div style={{ marginBottom: 20, display: "flex", gap: 12 }}>
        <div>
          <label htmlFor="roleFilter">Filter by Role</label><br />
          <select id="roleFilter" value={roleFilter} onChange={e => { setRoleFilter(e.target.value); setPage(0); }} style={{ minWidth: 120 }}>
            <option value="">All</option>
            {ROLE_OPTIONS.map(r => <option key={r}>{r}</option>)}
          </select>
        </div>
        <div>
          <label htmlFor="search">Search Name</label><br />
          <input id="search" value={search} onChange={e => { setSearch(e.target.value); setPage(0); }} />
        </div>
      </div>
      <div style={{ background: "var(--bg-white)", boxShadow: "var(--shadow-md)", borderRadius: 8, padding: 'var(--spacing-md)'}}>
        <table style={{ width: "100%", borderCollapse: "collapse" }}>
          <thead>
            <tr>
              <th style={{ cursor: "pointer" }} onClick={() => setSortBy("playerName")}>Player Name</th>
              <th style={{ cursor: "pointer" }} onClick={() => setSortBy("age")}>Age</th>
              <th style={{ cursor: "pointer" }} onClick={() => setSortBy("role")}>Role</th>
              <th style={{ cursor: "pointer" }} onClick={() => setSortBy("country")}>Country</th>
              <th>Actions</th>
            </tr>
          </thead>
          <tbody>
            {loading ? (
              <tr><td colSpan={5} className="empty-state">Loading...</td></tr>
            ) : players.length === 0 ? (
              <tr><td colSpan={5} className="empty-state">No players found.</td></tr>
            ) : (
              players.map((p, i) => (
                <tr key={p.playerId || i}>
                  <td style={{fontWeight:600, overflowWrap:'break-word', maxWidth:180}}>{p.playerName}</td>
                  <td>{p.age}</td>
                  <td>{p.role}</td>
                  <td>{p.country}</td>
                  <td>
                    <button className="btn-secondary" style={{ marginRight: 4 }} onClick={() => handleEdit(p)}>
                      Edit
                    </button>
                    <button className="btn-primary" onClick={() => handleDelete(p.playerId)}>
                      Delete
                    </button>
                  </td>
                </tr>
              ))
            )}
          </tbody>
        </table>
      </div>
      <div style={{ marginTop: 18, display: "flex", justifyContent: "space-between", alignItems: "center" }}>
        <div>
          <button className="btn-secondary" onClick={() => setPage(page - 1)} disabled={page === 0}>&lt; Prev</button>
          <span style={{ margin: '0 10px' }}>Page {page + 1} of {totalPages}</span>
          <button className="btn-secondary" onClick={() => setPage(page + 1)} disabled={page + 1 >= totalPages}>Next &gt;</button>
        </div>
        <div>
          <label htmlFor="size">Rows:</label>
          <select id="size" value={size} onChange={e => { setSize(Number(e.target.value)); setPage(0); }}>
            {[5, 10, 20].map(n => <option key={n} value={n}>{n}</option>)}
          </select>
        </div>
      </div>
    </div>
  );
}

export default PlayerTable;
