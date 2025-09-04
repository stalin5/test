import React, { useState, useEffect } from "react";
import Header from "./components/Header";
import PostForm from "./components/PostForm";
import PostList from "./components/PostList";
import './App.css'
import {
  getAllPosts,
  getPostsByHashtag,
  getPostsSortedByTime,
} from "./services/api";
import "./App.css";

function App() {
  const [posts, setPosts] = useState([]);
  const [filter, setFilter] = useState("all");

  const fetchPosts = async () => {
    try {
      let response;
      if (filter === "all") {
        response = await getAllPosts();
      } else if (filter === "sorted") {
        response = await getPostsSortedByTime();
      } else {
        response = await getPostsByHashtag(filter);
      }
      setPosts(response.data);
    } catch (error) {
      console.error("Error fetching posts:", error);
    }
  };

  useEffect(() => {
    fetchPosts();
  }, [filter]);

  return (
    <div className="app">
      <Header />
      <div className="controls">
        <select onChange={(e) => setFilter(e.target.value)} value={filter}>
          <option value="all">All Posts</option>
          <option value="sorted">Sorted by Time</option>
          <option value="#morning">#morning</option>
          <option value="#sunset">#sunset</option>
          <option value="#coffee">#coffee</option>
        </select>
      </div>
      <PostForm onAdd={fetchPosts} />
      <PostList posts={posts} onDelete={fetchPosts} />
    </div>
  );
}

export default App;
