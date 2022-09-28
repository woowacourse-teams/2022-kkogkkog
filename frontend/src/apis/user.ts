import { client } from '@/apis';
import {
  EditMeRequest,
  MeResponse,
  OAuthLoginRequest,
  OAuthLoginResponse,
  OAuthSignupRequest,
  OAuthSignupResponse,
  OAuthSlackAppDownloadRequest,
  SearchUserRequest,
  UserHistoryListResponse,
  UserListResponse,
} from '@/types/user/remote';

export const getMe = async () => {
  const { data } = await client.get<MeResponse>('/members/me');

  return data;
};

export const editMe = (body: EditMeRequest) => client.put('/members/me', body);

export const getUserList = async () => {
  const { data } = await client.get<UserListResponse>('/members');

  return data;
};

export const oAuthSignup = (body: OAuthSignupRequest) =>
  client.post<OAuthSignupResponse>('/signup/token', body);

export const oAuthLogin = ({ code }: OAuthLoginRequest) =>
  client.get<OAuthLoginResponse>(`/login/token?code=${code}`);

export const oAuthSlackAppDownload = ({ code }: OAuthSlackAppDownloadRequest) =>
  client.post('/install/bot', { code });

export const getUserHistoryList = async () => {
  const { data } = await client.get<UserHistoryListResponse>('/members/me/histories');

  return data;
};

export const readAllHistory = () => client.put('/members/me/histories');

export const readHistory = ({ id }: { id: number }) => client.put(`/members/me/histories/${id}`);

export const searchUser = async ({ nickname }: SearchUserRequest) => {
  const { data } = await client.get(`/members?nickname=${nickname}`);

  return data;
};
