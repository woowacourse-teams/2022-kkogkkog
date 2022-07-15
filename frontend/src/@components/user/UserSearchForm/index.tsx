import { css } from '@emotion/react';
import { useState } from 'react';

import Icon from '@/@components/@shared/Icon';
import Input from '@/@components/@shared/Input';
import theme from '@/styles/theme';

import * as Styled from './style';

const users = [
  {
    id: 1,
    name: '준찌',
    email: 'wnsgur8397@naver.com',
  },
  {
    id: 3,
    name: '정',
    email: 'wnsgur8397@naver.com',
  },
  {
    id: 4,
    name: '아서',
    email: 'wnsgur8397@naver.com',
  },
  {
    id: 5,
    name: '레오',
    email: 'wnsgur8397@naver.com',
  },
];

const UserSearchForm = props => {
  const { currentReceiverList, onSelectReceiver } = props;

  const [searchedUserList, setSearchedUserList] = useState(null);

  const onChangeSearchInput = e => {
    const {
      target: { value: searchUserNameValue },
    } = e;

    const findUserList = users.filter(({ name }) => name === searchUserNameValue);

    setSearchedUserList(findUserList);
  };

  return (
    <Styled.Root>
      {currentReceiverList.length !== 0 && (
        <Styled.SelectedContainer>
          {currentReceiverList.map(user => (
            <div key={user.id} onClick={() => onSelectReceiver(user)}>
              {user.name}
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

const UserSearchResult = props => {
  const { searchedUserList, currentReceiverList, onSelectReceiver } = props;

  if (searchedUserList === null) {
    return <Styled.TextContainer>🔍 유저를 찾아보세요 🔍</Styled.TextContainer>;
  }

  if (searchedUserList.length === 0) {
    return <Styled.TextContainer>😱 검색된 유저가 존재하지 않습니다. 😱</Styled.TextContainer>;
  }

  return searchedUserList.map(user => (
    <Styled.SearchedUser
      key={user.id}
      isSelected={currentReceiverList.some(receiver => receiver.id === user.id)}
      onClick={() => onSelectReceiver(user)}
    >
      {user.name}({user.email})
    </Styled.SearchedUser>
  ));
};

export default UserSearchForm;
