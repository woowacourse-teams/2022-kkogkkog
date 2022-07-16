import { client } from '@/apis';
import { KkogKkog } from '@/types/domain';
//@TODO transformer 객체 만들기

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

export const createKkogkkog = (info: Omit<KkogKkog, 'id' | 'thumbnail'>) =>
  client.post('/coupons', info);
