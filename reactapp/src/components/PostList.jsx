import React from "react";
import { deletePost } from "../services/api";

const PostList = ({ posts, onDelete }) => {
  const handleDelete = async (id) => {
    try {
      await deletePost(id);
      onDelete(); // refresh list
    } catch (error) {
      alert("Error deleting post: " + error.message);
    }
  };

  return (
    <div className="post-list">
      {posts.length === 0 ? (
        <p>No posts available</p>
      ) : (
        posts.map((post) => (
          <div key={post.id} className="post-card">
            <img src={post.imageUrl} alt="Post" className="post-img" />
            <div className="post-content">
              <h3>{post.caption}</h3>
              <p>{post.hashtags}</p>
              <small>{new Date(post.timestamp).toLocaleString()}</small>
              <br />
              <button onClick={() => handleDelete(post.id)}>Delete</button>
            </div>
          </div>
        ))
      )}
    </div>
  );
};

export default PostList;
