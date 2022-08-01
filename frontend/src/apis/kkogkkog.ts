import { client } from '@/apis';
import { ChangeKkogKkogStatusRequest, CreateKkogKkogRequest } from '@/types/remote/request';
import { KkogKkogListResponse, KkogKKogResponse } from '@/types/remote/response';
//@TODO transformer 객체 만들기

export const getKkogkkog = (id: number) => client.get<KkogKKogResponse>(`/coupons/${id}`);

export const getKkogkkogList = () => client.get<KkogKkogListResponse>('/coupons');

export const createKkogkkog = (info: CreateKkogKkogRequest) => client.post('/coupons', info);

export const changeKkogkkogStatus = ({
  id,
  body,
}: {
  id: number;
  body: ChangeKkogKkogStatusRequest;
}) => client.post(`/coupons/${id}/event`, body);
