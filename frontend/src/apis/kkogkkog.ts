import { client } from '@/apis';
import { KkogKkog, KkogKkogType } from '@/types/domain';
//@TODO transformer 객체 만들기

type CreateRequestKkogKKogBody = {
  receivers: number[];
  backgroundColor: string;
  modifier: string;
  couponType: KkogKkogType;
  message: string;
};

export const getKkogkkog = () =>
  client.get<KkogKkog[]>('/coupons', {
    transformResponse: stringResponse => {
      const parsedData = JSON.parse(stringResponse);

      if (parsedData.error) {
        return parsedData;
      }

      return parsedData.data;
    },
  });

export const createKkogkkog = (info: CreateRequestKkogKKogBody) => client.post('/coupons', info);
