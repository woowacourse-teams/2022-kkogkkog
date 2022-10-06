/** Request */

import { Member, UserCouponHistory } from './client';

export interface SignupRequest {
  nickname: string;
  accessToken: string;
}

export interface SlackAppDownloadRequest {
  code: string;
}

export interface LoginRequest {
  code: string;
}

export type EditMeRequest = Pick<MeResponse, 'nickname'>;

export interface SearchUserRequest {
  nickname: string;
}

/** Response */

export interface LoginResponse {
  accessToken: string;
  isNew: boolean;
}

export interface SignupResponse {
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
  data: Member[];
}
