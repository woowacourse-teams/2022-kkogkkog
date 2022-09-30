/** Request */

import { Member, UserCouponHistory } from './client';

interface SignupRequest {
  nickname: string;
  accessToken: string;
}

export type SlackSignupRequest = SignupRequest;
export type GoogleSignupRequest = SignupRequest;

export interface SlackAppDownloadRequest {
  code: string;
}

interface LoginRequest {
  code: string;
}

export type SlackLoginRequest = LoginRequest;
export type GoogleLoginRequest = LoginRequest;

export type EditMeRequest = Pick<MeResponse, 'nickname'>;

export interface SearchUserRequest {
  nickname: string;
}

/** Response */

interface LoginResponse {
  accessToken: string;
  isNew: boolean;
}

export type SlackLoginResponse = LoginResponse;
export type GoogleLoginResponse = LoginResponse;

export interface SignupResponse {
  accessToken: string;
}

export type SlackSignupResponse = SignupResponse;
export type GoogleSignupResponse = SignupResponse;

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
