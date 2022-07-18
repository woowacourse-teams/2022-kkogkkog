import { client } from '@/apis';
import { CreateKkogKkogRequest } from '@/types/remote/request';
import { KkogKkogListResponse, KkogKKogResponse } from '@/types/remote/response';
//@TODO transformer 객체 만들기

export const getKkogkkog = (id: number) => client.get<KkogKKogResponse>(`/coupons/${id}`);

export const getKkogkkogList = () =>
  client.get<KkogKkogListResponse>('/coupons', {
    transformResponse: stringResponse => {
      const parsedData = JSON.parse(stringResponse);

      if (parsedData.error) {
        return parsedData;
      }

      return parsedData.data;
    },
  });

export const createKkogkkog = (info: CreateKkogKkogRequest) => client.post('/coupons', info);
