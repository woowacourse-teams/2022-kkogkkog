import { client } from '@/apis';
import { OAuthType } from '@/types/user/client';
import {
  EditMeRequest,
  LoginRequest,
  LoginResponse,
  MeResponse,
  SearchUserRequest,
  SignupRequest,
  SignupResponse,
  SlackAppDownloadRequest,
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

export const oAuthSignup = (oAuthType: OAuthType) => (body: SignupRequest) => {
  const endpoint = `signup/${oAuthType === 'slack' ? 'token' : 'google'}`;

  return client.post<SignupResponse>(endpoint, body);
};

export const oAuthLogin =
  (oAuthType: OAuthType) =>
  ({ code }: LoginRequest) => {
    const endpoint = `login/${oAuthType === 'slack' ? 'token' : 'google'}`;

    return client.get<LoginResponse>(`${endpoint}?code=${code}`);
  };

export const slackAppDownload = ({ code }: SlackAppDownloadRequest) =>
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
