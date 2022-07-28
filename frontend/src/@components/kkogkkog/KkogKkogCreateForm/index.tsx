import { ChangeEventHandler, FormEventHandler } from 'react';

import Button from '@/@components/@shared/Button';
import Input from '@/@components/@shared/Input';
import SelectInput from '@/@components/@shared/SelectInput';
import UserSearchModal from '@/@components/user/UserSearchModal';
import { useModal } from '@/@hooks/@common/useModal';
import useUserList from '@/@hooks/user/useUserList';
import {
  KKOGKKOG_COLORS,
  KKOGKKOG_ENG_TYPE,
  KKOGKKOG_MODIFIERS,
  kkogkkogColors,
  kkogkkogModifiers,
  kkogkkogType,
} from '@/types/client/kkogkkog';
import { UserResponse } from '@/types/remote/response';

import * as Styled from './style';

interface KkogKkogCreateFormProps {
  currentReceiverList: UserResponse[];
  currentType: KKOGKKOG_ENG_TYPE;
  currentModifier: KKOGKKOG_MODIFIERS;
  currentColor: KKOGKKOG_COLORS;
  currentMessage: string;
  onSelectReceiver: (user: UserResponse) => void;
  onSelectType: (type: KKOGKKOG_ENG_TYPE) => void;
  onSelectModifier: (modifier: KKOGKKOG_MODIFIERS) => void;
  onSelectColor: (color: KKOGKKOG_COLORS) => void;
  onChangeMessage: ChangeEventHandler<HTMLInputElement>;
  onSubmitCreateForm: FormEventHandler<HTMLFormElement>;
}

const KkogKkogCreateForm = (props: KkogKkogCreateFormProps) => {
  const {
    currentReceiverList,
    currentType,
    currentModifier,
    currentColor,
    currentMessage,
    onSelectReceiver,
    onSelectType,
    onSelectModifier,
    onSelectColor,
    onChangeMessage,
    onSubmitCreateForm,
  } = props;

  const { isShowModal, openModal, closeModal } = useModal();

  return (
    <Styled.FormRoot onSubmit={onSubmitCreateForm}>
      <Styled.FindUserContainer>
        <div>누구에게 보내시나요?</div>
        <Styled.FindUserInput onClick={openModal}>
          {currentReceiverList.length === 0 && <span>유저를 찾아보세요</span>}

          {currentReceiverList.length !== 0 && (
            <Styled.SelectedUserListContainer>
              {currentReceiverList.map(receiver => (
                <Styled.SelectedUserContainer key={receiver.id}>
                  <span>{receiver.nickname}</span>
                </Styled.SelectedUserContainer>
              ))}
            </Styled.SelectedUserListContainer>
          )}

          <span>🔍</span>
        </Styled.FindUserInput>
      </Styled.FindUserContainer>

      {isShowModal && (
        <UserSearchModal
          currentReceiverList={currentReceiverList}
          onSelectReceiver={onSelectReceiver}
          closeModal={closeModal}
        />
      )}

      <SelectInput label='어떤 쿠폰인가요?'>
        {kkogkkogType.map(({ engType, imageUrl }) => (
          <Styled.TypeOption
            key={engType}
            isSelected={engType === currentType}
            onClick={() => onSelectType(engType)}
          >
            <img src={imageUrl} alt='hi' />
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput label='당신의 기분을 골라주세요'>
        {kkogkkogModifiers.map(modifier => (
          <Styled.FeelOption
            key={modifier}
            isSelected={modifier === currentModifier}
            onClick={() => onSelectModifier(modifier)}
          >
            #{modifier}
          </Styled.FeelOption>
        ))}
      </SelectInput>

      <Input
        label='하고 싶은 말을 적어주세요'
        placeholder='쿠폰을 사용하는 사람을 생각하며 적어주세요!'
        value={currentMessage}
        onChange={onChangeMessage}
      />

      <Styled.ButtonContainer>
        <Styled.ButtonInner>
          <Button>꼭꼭 발급하기</Button>
        </Styled.ButtonInner>
      </Styled.ButtonContainer>
    </Styled.FormRoot>
  );
};

export default KkogKkogCreateForm;
