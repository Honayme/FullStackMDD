import {User} from "./User";

export interface Comment {
  id: number;
  description: string;
  author: User;
  postId: number;
  createdAt: Date;
  updatedAt: Date;
  postTitle: string;
  topicTitle: string;
}
