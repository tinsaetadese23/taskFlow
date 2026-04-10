export type TaskStatus = 'pending' | 'in_progress' | 'completed' | 'failed';
export type TaskPriority = 'low' | 'medium' | 'high';

export interface Category {
    id: number;
    category_name: string;
    category_description: string;
}
export interface Task{
    id : number,
    title : string,
    description : string,
    status : TaskStatus,
    priority : TaskPriority,
    due_date : string,
    category? : Category
}