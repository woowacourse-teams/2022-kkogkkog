/** Request */

import { UserCouponHistory } from './client';

export interface SlackSignupRequest {
  nickname: string;
  accessToken: string;
}

export interface SlackAppDownloadRequest {
  code: string;
}

export interface SlackLoginRequest {
  code: string;
}

export type EditMeRequest = Pick<MeResponse, 'nickname'>;

export interface SearchUserRequest {
  nickname: string;
}

/** Response */

export interface SlackLoginResponse {
  accessToken: string;
  isNew: boolean;
}

export interface SlackSignupResponse {
  accessToken: string;
}

export interface UserResponse {
  id: number;
  userId: string;
  nickname: string;
  email: string;
  imageUrl: string;
}

export interface MeResponse extends UserResponse {
  workspaceId: string;
  workspaceName: string;
  unReadCount: number;
}

export interface UserHistoryListResponse {
  data: UserCouponHistory[];
}

export interface UserListResponse {
  data: UserResponse[];
}

export interface SearchUserResponse {
  data: Pick<UserResponse, 'id' | 'nickname' | 'imageUrl'>[];
}
