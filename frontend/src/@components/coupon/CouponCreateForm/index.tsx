import { ChangeEventHandler, FormEventHandler, MouseEventHandler } from 'react';
import { Link } from 'react-router-dom';

import Button from '@/@components/@shared/Button';
import SelectInput from '@/@components/@shared/SelectInput';
import UserSearchModal from '@/@components/user/UserSearchModal';
import { useModal } from '@/@hooks/@common/useModal';
import { usePreventReload } from '@/@hooks/@common/usePreventReload';
import { PATH } from '@/Router';
import {
  COUPON_ENG_TYPE,
  COUPON_HASHTAGS,
  couponHashtags,
  couponTypeCollection,
  THUMBNAIL,
} from '@/types/coupon/client';
import { UserResponse } from '@/types/user/remote';

import * as Styled from './style';

interface CouponCreateFormProps {
  currentReceiverList: UserResponse[];
  currentCouponType: COUPON_ENG_TYPE;
  currentCouponTag: COUPON_HASHTAGS;
  currentCouponMessage: string;
  onSelectReceiver: (user: UserResponse) => MouseEventHandler<HTMLDivElement>;
  onSelectCouponType: (type: COUPON_ENG_TYPE) => MouseEventHandler<HTMLLIElement>;
  onSelectCouponTag: (hashtag: COUPON_HASHTAGS) => MouseEventHandler<HTMLLIElement>;
  onChangeCouponMessage: ChangeEventHandler<HTMLTextAreaElement>;
  onSubmitCouponCreateForm: FormEventHandler<HTMLFormElement>;
}

const CouponCreateForm = (props: CouponCreateFormProps) => {
  const {
    currentReceiverList,
    currentCouponType,
    currentCouponTag,
    currentCouponMessage,
    onSelectReceiver,
    onSelectCouponType,
    onSelectCouponTag,
    onChangeCouponMessage,
    onSubmitCouponCreateForm,
  } = props;

  usePreventReload();

  const { isShowModal, openModal, closeModal } = useModal();

  return (
    <Styled.FormRoot onSubmit={onSubmitCouponCreateForm}>
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
        {/* <Link to={PATH.UNREGISTERED_COUPON_CREATE} css={Styled.AnotherCouponCreatePageLink} replace>
          미등록 쿠폰 생성하기
        </Link> */}
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
            isSelected={engType === currentCouponType}
            onClick={onSelectCouponType(engType)}
          >
            <img src={THUMBNAIL[engType]} alt='쿠폰 종류' width={50} height={50} />
          </Styled.TypeOption>
        ))}
      </SelectInput>

      <SelectInput.VerticalView label='당신의 마음을 선택해주세요 !'>
        {couponHashtags.map(hashtag => (
          <Styled.FeelOption
            key={hashtag}
            isSelected={hashtag === currentCouponTag}
            onClick={onSelectCouponTag(hashtag)}
          >
            #{hashtag}
          </Styled.FeelOption>
        ))}
      </SelectInput.VerticalView>

      <Styled.TextareaContainer>
        <label htmlFor='message-textarea'>메세지를 작성해보세요 (선택)</label>
        <Styled.MessageTextareaContainer>
          <Styled.MessageTextarea
            id='message-textarea'
            placeholder='시간, 장소 등 원하는 메시지를 보내보세요!'
            value={currentCouponMessage}
            onChange={onChangeCouponMessage}
          />
          <Styled.MessageLength>{currentCouponMessage.length} / 50</Styled.MessageLength>
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
