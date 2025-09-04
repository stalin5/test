import React, { useState } from "react";
import { addPost } from "../services/api";

const PostForm = ({ onAdd }) => {
  const [caption, setCaption] = useState("");
  const [imageUrl, setImageUrl] = useState("");
  const [hashtags, setHashtags] = useState("");

  const handleSubmit = async (e) => {
    e.preventDefault();
    const newPost = { caption, imageUrl, hashtags, timestamp: new Date() };

    try {
      await addPost(newPost);
      onAdd(); // refresh list
      setCaption("");
      setImageUrl("");
      setHashtags("");
    } catch (error) {
      alert("Error adding post: " + error.message);
    }
  };

  return (
    <form className="post-form" onSubmit={handleSubmit}>
      <input
        type="text"
        placeholder="Caption"
        value={caption}
        onChange={(e) => setCaption(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="Image URL"
        value={imageUrl}
        onChange={(e) => setImageUrl(e.target.value)}
        required
      />
      <input
        type="text"
        placeholder="#hashtags"
        value={hashtags}
        onChange={(e) => setHashtags(e.target.value)}
        required
      />
      <button type="submit">Add Post</button>
    </form>
  );
};

export default PostForm;
