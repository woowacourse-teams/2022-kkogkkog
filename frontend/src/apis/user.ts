import { client } from '@/apis';
import {
  EditMeRequest,
  LoginRequest,
  ReadHistoryRequest,
  SignupRequest,
} from '@/types/remote/request';
import {
  LoginResponse,
  MeResponse,
  OAuthLoginResponse,
  UserHistoryResponse,
  UserListResponse,
} from '@/types/remote/response';

export const getMe = async () => {
  const { data } = await client.get<MeResponse>('/members/me');

  return data;
};

export const editMe = (body: EditMeRequest) => client.put('/members/me', body);

export const getUserList = async () => {
  const { data } = await client.get<UserListResponse>('/members');

  return data;
};

export const signUpToken = (body: SignupRequest) => client.post('/signup/token', body);

export const login = (body: LoginRequest) => client.post<LoginResponse>('/login', body);

export const OAuthLogin = (code: string) =>
  client.get<OAuthLoginResponse>(`/login/token?code=${code}`);

export const getUserHistoryList = async () => {
  const { data } = await client.get<UserHistoryResponse>('/members/me/histories');

  return data;
};

export const readAllHistory = () => client.put('members/me/histories');

export const readHistory = ({ id }: ReadHistoryRequest) =>
  client.put(`/members/me/histories/${id}`);
