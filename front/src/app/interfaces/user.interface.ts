import {Topics} from "./topics.interface";
export interface User {
  topics: Topics[];
	id: number,
	name: string,
	email: string,
	created_at: Date,
	updated_at: Date
}
