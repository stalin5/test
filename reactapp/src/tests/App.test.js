// __tests__/playerApi.test.js
import { render, screen } from "@testing-library/react";
import axios from "axios";
import {
  addPlayer,
  getAllPlayers,
  getPlayersByRole,
  getPlayersSortedByTeam,
  deletePlayer,
} from "../services/api";
import Header from "../components/Header";

// Mock axios
jest.mock("axios");

describe("Header Component", () => {
  test("renders the main title correctly", () => {
    render(<Header />);
    const titleElement = screen.getByText(/IPL Team Management/i);
    expect(titleElement).toBeInTheDocument();
  });

  test("renders the subtitle correctly", () => {
    render(<Header />);
    const subtitleElement = screen.getByText(/Manage your players and teams efficiently/i);
    expect(subtitleElement).toBeInTheDocument();
  });
});


describe("Player API Tests", () => {
  const mockPlayer = {
    id: "1",
    name: "Virat Kohli",
    team: "RCB",
    role: "Batsman",
    age: 34,
    runs: 12000,
  };

  // ------------------- addPlayer Tests -------------------
  test("addPlayer_should_post_new_player", async () => {
    axios.post.mockResolvedValue({ data: mockPlayer });
    const result = await addPlayer(mockPlayer);
    expect(result.data).toEqual(mockPlayer);
    expect(axios.post).toHaveBeenCalledWith(
      "https://8080-fddecedccde329052728bccfaccecftwo.premiumproject.examly.io/api/players/addPlayer",
      mockPlayer
    );
  });

  test("addPlayer_should_handle_error", async () => {
    axios.post.mockRejectedValue(new Error("Network Error"));
    await expect(addPlayer(mockPlayer)).rejects.toThrow("Network Error");
  });

  test("addPlayer_should_call_with_correct_url", async () => {
    axios.post.mockResolvedValue({ data: mockPlayer });
    await addPlayer(mockPlayer);
    expect(axios.post).toHaveBeenCalledWith(
      expect.stringContaining("/addPlayer"),
      mockPlayer
    );
  });

  test("addPlayer_should_return_player_id", async () => {
    axios.post.mockResolvedValue({ data: { id: "123" } });
    const result = await addPlayer(mockPlayer);
    expect(result.data.id).toBe("123");
  });

  // ------------------- getAllPlayers Tests -------------------
  test("getAllPlayers_should_fetch_all_players", async () => {
    axios.get.mockResolvedValue({ data: [mockPlayer] });
    const result = await getAllPlayers();
    expect(result.data).toEqual([mockPlayer]);
  });

  test("getAllPlayers_should_handle_empty_list", async () => {
    axios.get.mockResolvedValue({ data: [] });
    const result = await getAllPlayers();
    expect(result.data.length).toBe(0);
  });

  test("getAllPlayers_should_throw_error_on_failure", async () => {
    axios.get.mockRejectedValue(new Error("Server Down"));
    await expect(getAllPlayers()).rejects.toThrow("Server Down");
  });

  test("getAllPlayers_should_return_array", async () => {
    axios.get.mockResolvedValue({ data: [mockPlayer, mockPlayer] });
    const result = await getAllPlayers();
    expect(Array.isArray(result.data)).toBe(true);
  });

  // ------------------- getPlayersByRole Tests -------------------
  test("getPlayersByRole_should_fetch_by_role", async () => {
    axios.get.mockResolvedValue({ data: [mockPlayer] });
    const result = await getPlayersByRole("Batsman");
    expect(result.data[0].role).toBe("Batsman");
  });

  test("getPlayersByRole_should_call_correct_url", async () => {
    axios.get.mockResolvedValue({ data: [mockPlayer] });
    await getPlayersByRole("Bowler");
    expect(axios.get).toHaveBeenCalledWith(
      expect.stringContaining("role=Bowler")
    );
  });

  test("getPlayersByRole_should_handle_no_players", async () => {
    axios.get.mockResolvedValue({ data: [] });
    const result = await getPlayersByRole("Wicketkeeper");
    expect(result.data).toEqual([]);
  });

  test("getPlayersByRole_should_throw_error", async () => {
    axios.get.mockRejectedValue(new Error("Invalid role"));
    await expect(getPlayersByRole("Unknown")).rejects.toThrow("Invalid role");
  });

  // ------------------- getPlayersSortedByTeam Tests -------------------
  test("getPlayersSortedByTeam_should_fetch_sorted_list", async () => {
    const players = [
      { id: "1", team: "CSK" },
      { id: "2", team: "MI" },
    ];
    axios.get.mockResolvedValue({ data: players });
    const result = await getPlayersSortedByTeam();
    expect(result.data[0].team).toBe("CSK");
  });

  test("getPlayersSortedByTeam_should_return_array", async () => {
    axios.get.mockResolvedValue({ data: [mockPlayer] });
    const result = await getPlayersSortedByTeam();
    expect(Array.isArray(result.data)).toBe(true);
  });

  test("getPlayersSortedByTeam_should_throw_error", async () => {
    axios.get.mockRejectedValue(new Error("Sort Error"));
    await expect(getPlayersSortedByTeam()).rejects.toThrow("Sort Error");
  });

  test("getPlayersSortedByTeam_should_match_length", async () => {
    const players = [mockPlayer, mockPlayer];
    axios.get.mockResolvedValue({ data: players });
    const result = await getPlayersSortedByTeam();
    expect(result.data.length).toBe(2);
  });

  // ------------------- deletePlayer Tests -------------------
  test("deletePlayer_should_delete_player", async () => {
    axios.delete.mockResolvedValue({ data: { success: true } });
    const result = await deletePlayer("1");
    expect(result.data.success).toBe(true);
  });

  test("deletePlayer_should_call_correct_url", async () => {
    axios.delete.mockResolvedValue({ data: {} });
    await deletePlayer("1");
    expect(axios.delete).toHaveBeenCalledWith(
      expect.stringContaining("/1")
    );
  });

  test("deletePlayer_should_throw_error", async () => {
    axios.delete.mockRejectedValue(new Error("Delete failed"));
    await expect(deletePlayer("999")).rejects.toThrow("Delete failed");
  });

  test("deletePlayer_should_return_object", async () => {
    axios.delete.mockResolvedValue({ data: { success: true } });
    const result = await deletePlayer("1");
    expect(typeof result.data).toBe("object");
  });
});
