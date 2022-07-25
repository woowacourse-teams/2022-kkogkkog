import { ChangeEvent } from 'react';

import Input from '@/@components/@shared/Input';
import { useSearchUser } from '@/@hooks/user/useSearchUser';
import { UserListResponse, UserResponse } from '@/types/remote/response';

import * as Styled from './style';

interface UserSearchFormProps {
  currentReceiverList: UserListResponse;
  onSelectReceiver: (user: UserResponse) => void;
}

const UserSearchForm = (props: UserSearchFormProps) => {
  const { currentReceiverList, onSelectReceiver } = props;

  const { searchedUserList, searchUser } = useSearchUser();

  const onChangeSearchInput = (e: ChangeEvent<HTMLInputElement>) => {
    const {
      target: { value: searchInput },
    } = e;

    searchUser(searchInput);
  };

  return (
    <Styled.Root>
      {currentReceiverList.length !== 0 && (
        <Styled.SelectedUserListContainer>
          {currentReceiverList.map(user => (
            <Styled.SelectedUserContainer key={user.id} onClick={() => onSelectReceiver(user)}>
              <span> {user.nickname}</span>
            </Styled.SelectedUserContainer>
          ))}
        </Styled.SelectedUserListContainer>
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
  searchedUserList: UserListResponse | undefined;
  currentReceiverList: UserListResponse;
  onSelectReceiver: (user: UserResponse) => void;
}

const UserSearchResult = (props: UserSearchResultProps) => {
  const { searchedUserList, currentReceiverList, onSelectReceiver } = props;

  if (searchedUserList === undefined) {
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
