import { Topics } from "./topics.interface";
import { User } from "./user.interface";

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
