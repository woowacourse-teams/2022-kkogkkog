import { client } from '@/apis';
import { EditMeRequest, JoinRequest, LoginRequest } from '@/types/remote/request';
import {
  LoginResponse,
  MeResponse,
  OAuthLoginResponse,
  UserListResponse,
} from '@/types/remote/response';

export const getMe = () => client.get<MeResponse>('/members/me');

export const editMe = (body: EditMeRequest) => client.put('/members/me', body);

export const getUserList = () => client.get<UserListResponse>('/members');

export const join = (body: JoinRequest) => client.post('/members', body);

export const login = (body: LoginRequest) => client.post<LoginResponse>('/login', body);

export const OAuthLogin = (code: string) =>
  client.get<OAuthLoginResponse>(`/login/token?code=${code}`);
