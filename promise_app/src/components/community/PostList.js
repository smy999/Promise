import React, { useState } from 'react';
import {View, Text, StyleSheet, FlatList, TouchableHighlight } from 'react-native';
import { useNavigation } from '@react-navigation/native';
import useCommunity from '../../utils/useCommunity';
import { useSelector, useDispatch } from 'react-redux';
import { getCommunityAPI } from '../../utils/axios';
import { getPostDetailAction, getMoreCommunityAction } from '../../modules/community/actions';
import moment from 'moment-timezone'

const PostList = () => {
  
  const dispatch = useDispatch()
  const navigation = useNavigation();
  const pageNum = useSelector(state => state.community.pageNum)
  const hasMore = useSelector(state => state.community.hasMore)
  const communityList = useSelector(state => state.community.communityList)
  const totalPageCnt = useSelector(state => state.community.totalPageCnt)
  const [onEndReachedCalledDuringMomentum, setOnEndReachedCalledDuringMomentum] = useState(false)


  const getMorePost = async () => {
    if (hasMore) {
      const res = await useCommunity(communityList, pageNum, totalPageCnt)
      dispatch(getMoreCommunityAction(res))
    }
  }
  
  const getCommunityDetail = (post) => {
     getCommunityAPI.detail(post.commuId).then((res)=>{
       dispatch(getPostDetailAction(res))
     }).then(()=>{
       navigation.navigate('communitydetail', {post: post, postDate: post.commuDate})
     })
  }

  return (
    <FlatList
      data={communityList}
      onEndReachedThreshold = {0.5}
      onMomentumScrollBegin = {() => {setOnEndReachedCalledDuringMomentum(false)}}
      onEndReached = {() => {
          if (!onEndReachedCalledDuringMomentum) {
            getMorePost()
            setOnEndReachedCalledDuringMomentum(true)
          }
        }
      }
      renderItem={({item, index}) => {

        const subDate = item.commuDate;
        const postDate = moment(subDate).tz("Asia/Seoul").format("YYYY.MM.DD HH:mm")
        const isLastPost = (communityList.length === index+1)

      return (
        isLastPost
        ? 
        <TouchableHighlight onPress={()=>getCommunityDetail(item)} underlayColor="white">
          <View style={styles.container} key={item.commuId}>
              <View style={styles.subContainer}>
                  <Text style={styles.itemNameText}>{item.userNickname}</Text>
                  <Text style={styles.itemDateText}>{postDate}</Text>
              </View>
              <View>
                  <Text style={styles.itemTitleText}>{item.commuTitle}</Text>
              </View>
          </View>
        </TouchableHighlight>
        :
        <>
        <TouchableHighlight onPress={()=>getCommunityDetail(item)} underlayColor="white">
          <View style={styles.container} key={item.commuId}>
              <View style={styles.subContainer}>
                  <Text style={styles.itemNameText}>{item.userNickname}</Text>
                  <Text style={styles.itemDateText}>{postDate}</Text>
              </View>
              <View>
                  <Text style={styles.itemTitleText}>{item.commuTitle}</Text>
              </View>
          </View>
        </TouchableHighlight>
        </>
      )}}
    />
  );
}

const styles = StyleSheet.create({
  container: {
    marginTop: 1,
    marginHorizontal: 0,
    paddingVertical: 12,
    paddingHorizontal: 14,
    minheight:90,
    shadowColor: '#f1f2f3',
    shadowOffset: {
      width: 0,
      height: 0,
    },
    shadowOpacity: 1,
    shadowRadius: 18.95,
    elevation: 1,
    zIndex: 1,
    backgroundColor: '#F4F4F4',
    borderBottomColor: '#D1D1D1',
    borderBottomWidth: 1,
  },
  subContainer: {
    flexDirection:'row', 
    alignItems:'center', 
    justifyContent:'space-between'
  },
  itemNameText: {
    fontSize: 16,
    fontWeight: '600'
  },
  itemTitleText: {
    fontSize: 16,
    fontWeight: '400',
    paddingVertical: 15,
  },
  itemDateText: {
    textAlign: 'right',
    fontSize: 12,
    fontWeight: '500',
  },
});

export default PostList