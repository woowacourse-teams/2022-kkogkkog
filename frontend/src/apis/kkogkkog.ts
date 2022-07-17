import { client } from '@/apis';
import { KkogKkog, KKOGKKOG_ENG_TYPE } from '@/types/domain';
//@TODO transformer 객체 만들기

type CreateRequestKkogKKogBody = {
  receivers: number[];
  backgroundColor: string;
  modifier: string;
  couponType: KKOGKKOG_ENG_TYPE;
  message: string;
};

export const getKkogkkog = id => client.get<KkogKkog>(`/coupons/${id}`);

export const getKkogkkogList = () =>
  client.get<{ received: KkogKkog[]; sent: KkogKkog[] }>('/coupons', {
    transformResponse: stringResponse => {
      const parsedData = JSON.parse(stringResponse);

      if (parsedData.error) {
        return parsedData;
      }

      return parsedData.data;
    },
  });

export const createKkogkkog = (info: CreateRequestKkogKKogBody) => client.post('/coupons', info);
