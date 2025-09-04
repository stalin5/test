import axios from "axios";

const API_BASE = "https://8080-fddecedccde329052728bccfaccecftwo.premiumproject.examly.io/api/posts";

export const addPost = async (post) => {
  return await axios.post(`${API_BASE}/addPost`, post);
};

export const getAllPosts = async () => {
  return await axios.get(`${API_BASE}/allPosts`);
};

export const getPostsByHashtag = async (tag) => {
  return await axios.get(`${API_BASE}/byHashtag?tag=${tag}`);
};

export const getPostsSortedByTime = async () => {
  return await axios.get(`${API_BASE}/sortedByTime`);
};

export const deletePost = async (id) => {
  return await axios.delete(`${API_BASE}/${id}`);
};
