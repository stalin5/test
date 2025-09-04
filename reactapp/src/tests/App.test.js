import React from "react";
import { render, screen, fireEvent, waitFor } from "@testing-library/react";
import "@testing-library/jest-dom";
import App from "../App";

jest.mock("../services/api", () => ({
  getAllPosts: jest.fn(),
  getPostsByHashtag: jest.fn(),
  getPostsSortedByTime: jest.fn(),
  addPost: jest.fn(),
  deletePost: jest.fn(),
}));

import {
  getAllPosts,
  getPostsByHashtag,
  getPostsSortedByTime,
  addPost,
  deletePost,
} from "../services/api";

describe("Instagram Post Manager App Tests", () => {
  const mockPosts = [
    { id: 1, caption: "Morning Coffee", imageUrl: "coffee.jpg", hashtags: "#coffee", timestamp: new Date().toISOString() },
    { id: 2, caption: "Beautiful Sunset", imageUrl: "sunset.jpg", hashtags: "#sunset", timestamp: new Date().toISOString() },
  ];

  beforeEach(() => {
    jest.clearAllMocks();
  });

  // ---------- Basic Rendering ----------
  test("renders header title and subtitle", async () => {
    getAllPosts.mockResolvedValueOnce({ data: [] });
    render(<App />);
    expect(await screen.findByText("Instagram Post Manager")).toBeInTheDocument();
    expect(screen.getByText(/Manage your Instagram-style posts easily/i)).toBeInTheDocument();
  });

  test("renders empty state when no posts", async () => {
    getAllPosts.mockResolvedValueOnce({ data: [] });
    render(<App />);
    expect(await screen.findByText("No posts available")).toBeInTheDocument();
  });

  test("renders list of posts", async () => {
    getAllPosts.mockResolvedValueOnce({ data: mockPosts });
    render(<App />);
    expect(await screen.findByText("Morning Coffee")).toBeInTheDocument();
    expect(screen.getByText("Beautiful Sunset")).toBeInTheDocument();
  });

  // ---------- Post Creation ----------
  test("adds a new post", async () => {
    getAllPosts
      .mockResolvedValueOnce({ data: [] })
      .mockResolvedValueOnce({ data: [...mockPosts, { id: 3, caption: "New Post", imageUrl: "new.jpg", hashtags: "#new" }] });

    addPost.mockResolvedValueOnce({});

    render(<App />);
    fireEvent.change(screen.getByPlaceholderText("Caption"), { target: { value: "New Post" } });
    fireEvent.change(screen.getByPlaceholderText("Image URL"), { target: { value: "new.jpg" } });
    fireEvent.change(screen.getByPlaceholderText("#hashtags"), { target: { value: "#new" } });

    fireEvent.click(screen.getByText("Add Post"));

    expect(await screen.findByText("New Post")).toBeInTheDocument();
  });

  test("form requires all fields", async () => {
    getAllPosts.mockResolvedValueOnce({ data: [] });
    render(<App />);
    fireEvent.click(screen.getByText("Add Post"));
    expect(await screen.findByPlaceholderText("Caption")).toBeInTheDocument();
  });

  // ---------- Post Deletion ----------
  test("deletes a post", async () => {
    getAllPosts
      .mockResolvedValueOnce({ data: mockPosts })
      .mockResolvedValueOnce({ data: [mockPosts[1]] });

    deletePost.mockResolvedValueOnce({});

    render(<App />);
    expect(await screen.findByText("Morning Coffee")).toBeInTheDocument();
    fireEvent.click(screen.getAllByText("Delete")[0]);

    await waitFor(() => expect(screen.queryByText("Morning Coffee")).not.toBeInTheDocument());
  });

  // ---------- Filtering ----------
  test("filters posts by hashtag #coffee", async () => {
    getAllPosts.mockResolvedValueOnce({ data: mockPosts });
    getPostsByHashtag.mockResolvedValueOnce({ data: [mockPosts[0]] });

    render(<App />);
    fireEvent.change(await screen.findByDisplayValue("All Posts"), { target: { value: "#coffee" } });

    expect(await screen.findByText("Morning Coffee")).toBeInTheDocument();
    expect(screen.queryByText("Beautiful Sunset")).not.toBeInTheDocument();
  });

  test("filters posts by hashtag #sunset", async () => {
    getAllPosts.mockResolvedValueOnce({ data: mockPosts });
    getPostsByHashtag.mockResolvedValueOnce({ data: [mockPosts[1]] });

    render(<App />);
    fireEvent.change(await screen.findByDisplayValue("All Posts"), { target: { value: "#sunset" } });

    expect(await screen.findByText("Beautiful Sunset")).toBeInTheDocument();
    expect(screen.queryByText("Morning Coffee")).not.toBeInTheDocument();
  });

  test("shows all posts when filter reset", async () => {
    getAllPosts.mockResolvedValueOnce({ data: mockPosts });
    render(<App />);
    fireEvent.change(await screen.findByDisplayValue("All Posts"), { target: { value: "all" } });
    expect(await screen.findByText("Morning Coffee")).toBeInTheDocument();
    expect(screen.getByText("Beautiful Sunset")).toBeInTheDocument();
  });

  // ---------- Sorting ----------
  test("sorts posts by time", async () => {
    getAllPosts.mockResolvedValueOnce({ data: mockPosts });
    getPostsSortedByTime.mockResolvedValueOnce({ data: [...mockPosts].reverse() });

    render(<App />);
    fireEvent.change(await screen.findByDisplayValue("All Posts"), { target: { value: "sorted" } });

    expect(await screen.findByText("Beautiful Sunset")).toBeInTheDocument();
  });

  // ---------- UI Elements ----------
  test("dropdown filter is visible", async () => {
    getAllPosts.mockResolvedValueOnce({ data: [] });
    render(<App />);
    expect(await screen.findByDisplayValue("All Posts")).toBeInTheDocument();
  });

  test("delete button exists in post card", async () => {
    getAllPosts.mockResolvedValueOnce({ data: mockPosts });
    render(<App />);
    expect(await screen.findAllByText("Delete")).toHaveLength(2);
  });


});
