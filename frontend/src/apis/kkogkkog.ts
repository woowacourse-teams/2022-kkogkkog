import { client } from '@/apis';
import { KkogKkog } from '@/types/domain';

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

export const createKkogkkog = (info: Omit<KkogKkog, 'id'>) => client.post('/coupons', info);
