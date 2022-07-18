import { css } from '@emotion/react';
import { ChangeEvent, useState } from 'react';

import Icon from '@/@components/@shared/Icon';
import Input from '@/@components/@shared/Input';
import theme from '@/styles/theme';
import { UserListResponse, UserResponse } from '@/types/remote/response';

import * as Styled from './style';

const users = [
  {
    id: 1,
    nickname: '준찌',
    email: 'wnsgur8397@naver.com',
  },
  {
    id: 3,
    nickname: '정',
    email: 'wnsgur8397@naver.com',
  },
  {
    id: 4,
    nickname: '아서',
    email: 'wnsgur8397@naver.com',
  },
  {
    id: 5,
    nickname: '레오',
    email: 'wnsgur8397@naver.com',
  },
];

interface UserSearchFormProps {
  currentReceiverList: UserListResponse;
  onSelectReceiver: (user: UserResponse) => void;
}

const UserSearchForm = (props: UserSearchFormProps) => {
  const { currentReceiverList, onSelectReceiver } = props;

  const [searchedUserList, setSearchedUserList] = useState<UserListResponse | null>(null);

  const onChangeSearchInput = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: searchUserNameValue },
    } = e;

    const findUserList = users.filter(({ nickname }) => nickname === searchUserNameValue);

    setSearchedUserList(findUserList);
  };

  return (
    <Styled.Root>
      {currentReceiverList.length !== 0 && (
        <Styled.SelectedContainer>
          {currentReceiverList.map(user => (
            <div key={user.id} onClick={() => onSelectReceiver(user)}>
              {user.nickname}
              <Icon
                iconName='close'
                size='15'
                color={theme.colors.grey_400}
                css={css`
                  margin-left: 5px;
                `}
              />
            </div>
          ))}
        </Styled.SelectedContainer>
      )}

      <Input.HiddenLabel
        label='누구에게 주고 싶나요?'
        placeholder='🔍 유저 검색'
        onChange={onChangeSearchInput}
        autoFocus
      />

      <Styled.SearchContainer>
        <UserSearchResult
          searchedUserList={searchedUserList}
          currentReceiverList={currentReceiverList}
          onSelectReceiver={onSelectReceiver}
        />
      </Styled.SearchContainer>
    </Styled.Root>
  );
};

interface UserSearchResultProps {
  searchedUserList: UserListResponse | null;
  currentReceiverList: UserListResponse;
  onSelectReceiver: (user: UserResponse) => void;
}

const UserSearchResult = (props: UserSearchResultProps) => {
  const { searchedUserList, currentReceiverList, onSelectReceiver } = props;

  if (searchedUserList === null) {
    return <Styled.TextContainer>🔍 유저를 찾아보세요 🔍</Styled.TextContainer>;
  }

  if (searchedUserList.length === 0) {
    return <Styled.TextContainer>😱 검색된 유저가 존재하지 않습니다. 😱</Styled.TextContainer>;
  }

  return (
    <>
      {searchedUserList.map(user => (
        <Styled.SearchedUser
          key={user.id}
          isSelected={currentReceiverList.some(receiver => receiver.id === user.id)}
          onClick={() => onSelectReceiver(user)}
        >
          {user.nickname}({user.email})
        </Styled.SearchedUser>
      ))}
    </>
  );
};

export default UserSearchForm;
