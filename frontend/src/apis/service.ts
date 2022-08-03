import { client } from '@/apis';

export const AddSlackApp = (code: string) => client.post('/install/bot', { code });
