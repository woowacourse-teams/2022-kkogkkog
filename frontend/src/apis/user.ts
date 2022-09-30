import { client } from '@/apis';
import {
  EditMeRequest,
  GoogleLoginRequest,
  GoogleLoginResponse,
  GoogleSignupRequest,
  GoogleSignupResponse,
  MeResponse,
  SearchUserRequest,
  SlackAppDownloadRequest,
  SlackLoginRequest,
  SlackLoginResponse,
  SlackSignupRequest,
  SlackSignupResponse,
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

export const slackSignup = (body: SlackSignupRequest) =>
  client.post<SlackSignupResponse>('/signup/token', body);

export const googleSignup = (body: GoogleSignupRequest) =>
  client.post<GoogleSignupResponse>('/signup/token', body);

export const slackLogin = ({ code }: SlackLoginRequest) =>
  client.get<SlackLoginResponse>(`/login/token?code=${code}`);

export const googleLogin = ({ code }: GoogleLoginRequest) =>
  client.get<GoogleLoginResponse>(`/login/google?code=${code}`);

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
