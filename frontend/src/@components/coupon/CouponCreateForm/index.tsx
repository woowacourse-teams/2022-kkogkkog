import { ChangeEventHandler, FormEventHandler } from 'react';

import Button from '@/@components/@shared/Button';
import SelectInput from '@/@components/@shared/SelectInput';
import UserSearchModal from '@/@components/user/UserSearchModal';
import { useModal } from '@/@hooks/@common/useModal';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import {
  COUPON_COLORS,
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  couponHashtags,
  couponTypeCollection,
  THUMBNAIL,
} from '@/types/client/coupon';
import { UserResponse } from '@/types/remote/response';

import * as Styled from './style';

interface CouponCreateFormProps {
  currentReceiverList: UserResponse[];
  currentType: COUPON_ENG_TYPE;
  currentHashtag: COUPON_HASHTAGS;
  currentColor: COUPON_COLORS;
  currentDescription: string;
  onSelectReceiver: (user: UserResponse) => void;
  onSelectType: (type: COUPON_ENG_TYPE) => void;
  onSelectHashtag: (hashtag: COUPON_HASHTAGS) => void;
  onSelectColor: (color: COUPON_COLORS) => void;
  onChangeDescription: ChangeEventHandler<HTMLTextAreaElement>;
  onSubmitCreateForm: FormEventHandler<HTMLFormElement>;
}

const CouponCreateForm = (props: CouponCreateFormProps) => {
  const {
    currentReceiverList,
    currentType,
    currentHashtag,
    currentDescription,
    onSelectReceiver,
    onSelectType,
    onSelectHashtag,
    onChangeDescription,
    onSubmitCreateForm,
  } = props;

  usePreventReload();

  const { isShowModal, openModal, closeModal } = useModal();

  return (
    <Styled.FormRoot onSubmit={onSubmitCreateForm}>
      <Styled.FindUserContainer>
        <div>누구에게 보내시나요 ?</div>
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

      <SelectInput label='어떤 쿠폰인가요 ?'>
        {couponTypeCollection.map(({ engType }) => (
          <Styled.TypeOption
            key={engType}
            isSelected={engType === currentType}
            onClick={() => onSelectType(engType)}
          >
            <img src={THUMBNAIL[engType]} alt='hi' />
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput label='당신의 마음을 선택해주세요 !'>
        {couponHashtags.map(hashtag => (
          <Styled.FeelOption
            key={hashtag}
            isSelected={hashtag === currentHashtag}
            onClick={() => onSelectHashtag(hashtag)}
          >
            #{hashtag}
          </Styled.FeelOption>
        ))}
      </SelectInput>

      <Styled.TextareaContainer>
        <label htmlFor='message-textarea'>메세지를 작성해보세요 (선택)</label>
        <Styled.MessageTextareaContainer>
          <Styled.MessageTextarea
            id='message-textarea'
            placeholder='시간, 장소 등 원하는 메시지를 보내보세요!'
            value={currentDescription}
            onChange={onChangeDescription}
          />
          <Styled.MessageLength>{currentDescription.length} / 50</Styled.MessageLength>
        </Styled.MessageTextareaContainer>
      </Styled.TextareaContainer>

      <Styled.ButtonContainer>
        <Styled.ButtonInner>
          <Button>쿠폰 보내기</Button>
        </Styled.ButtonInner>
      </Styled.ButtonContainer>
    </Styled.FormRoot>
  );
};

export default CouponCreateForm;
