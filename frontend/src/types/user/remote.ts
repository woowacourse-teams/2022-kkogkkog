// export interface ReadHistoryRequest {
//   id: number;
// }

// export interface LoginResponse {
//   accessToken: string;
// }

// export interface LoginRequest {
//   email: string;
//   password: string;
// }

/** Request */

import { UserCouponHistory } from './client';

export interface OAuthSignupRequest {
  nickname: string;
  accessToken: string;
}

export interface OAuthSlackAppDownloadRequest {
  code: string;
}

export type EditMeRequest = Pick<MeResponse, 'nickname'>;

/** Response */

export interface OAuthLoginResponse {
  accessToken: string;
  isNew: boolean;
}

export interface OAuthSignupResponse {
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
