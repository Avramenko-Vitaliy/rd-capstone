import { Status } from '@/types/models/status';

export interface Todo {
  id: number;
  name: string;
  description?: string;
  status: Status;
  date: string;
}
