import { Topics } from "./Topics";
import { User } from "./User";

export interface Post {
  [x: string]: any;
  id: number,
  topic: Topics,
  authors: User,
  title: string,
  description: string,
  topics: string,
  createdAt: Date,
  updatedAt: Date,
}
