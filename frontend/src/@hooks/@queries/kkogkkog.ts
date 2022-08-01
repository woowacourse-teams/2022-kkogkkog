import { useMutation, useQuery, useQueryClient } from 'react-query';
import { useNavigate } from 'react-router-dom';

import { changeKkogkkogStatus, createKkogkkog, getKkogkkogList } from '@/apis/kkogkkog';
import { PATH } from '@/Router';

const QUERY_KEY = {
  kkogkkogList: 'kkogkkogList',
};

export const useFetchKkogKkogList = () =>
  useQuery([QUERY_KEY.kkogkkogList], getKkogkkogList, {
    suspense: true,
    select(data) {
      return data.data;
    },
  });

export const useCreateKkogKkogMutation = () => {
  const navigate = useNavigate();

  return useMutation(createKkogkkog, {
    onSuccess() {
      navigate(PATH.LANDING, {
        state: {
          action: 'create',
        },
      });
    },
    onError() {
      alert('입력창을 확인하고 다시 시도해주세요.');
    },
  });
};

export const useChangeKkogKkogStatusMutation = () => {
  const queryClient = useQueryClient();

  return useMutation(changeKkogkkogStatus, {
    onSuccess() {
      queryClient.invalidateQueries(QUERY_KEY.kkogkkogList);
    },
    onError() {
      alert('잘못된 접근입니다. 다시 시도해주세요.');
    },
  });
};
