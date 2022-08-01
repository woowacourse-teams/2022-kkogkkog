import { client } from '@/apis';
import { JoinRequest, LoginRequest } from '@/types/remote/request';
import { LoginResponse, MeResponse, UserListResponse } from '@/types/remote/response';

export const getMe = () => client.get<MeResponse>('/members/me');

export const getUserList = () => client.get<UserListResponse>('/members');

export const join = (args: JoinRequest) => client.post('/members', args);

export const login = (args: LoginRequest) => client.post('/login', args);

export const OAuthLogin = (code: string) => client.get<LoginResponse>(`/login/token?code=${code}`);
