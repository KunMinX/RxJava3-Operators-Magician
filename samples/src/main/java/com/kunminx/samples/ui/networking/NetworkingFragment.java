package com.kunminx.samples.ui.networking;

import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kunminx.samples.R;
import com.kunminx.samples.databinding.FragmentNetworkingBinding;
import com.kunminx.samples.model.ApiUser;
import com.kunminx.samples.model.User;
import com.kunminx.samples.model.UserDetail;
import com.kunminx.samples.net.rx3androidnetworking.Rx3AndroidNetworking;
import com.kunminx.samples.utils.Utils;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableSource;
import io.reactivex.rxjava3.core.Observer;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.functions.Predicate;
import io.reactivex.rxjava3.schedulers.Schedulers;

/**
 * Created by amitshekhar on 04/02/17.
 */

public class NetworkingFragment extends Fragment {

  public static final String TAG = NetworkingFragment.class.getSimpleName();
  private FragmentNetworkingBinding mBinding;

  @Nullable
  @Override
  public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_networking, container, false);
    mBinding = FragmentNetworkingBinding.bind(view);
    mBinding.setClickProxy(new ClickProxy());
    return view;
  }

  @Override
  public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
    super.onViewCreated(view, savedInstanceState);

  }

  private Observable<List<User>> getCricketFansObservable() {
    return Rx3AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllCricketFans")
            .build()
            .getObjectListObservable(User.class);
  }

  /*
   * This observable return the list of User who loves Football
   */
  private Observable<List<User>> getFootballFansObservable() {
    return Rx3AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFootballFans")
            .build()
            .getObjectListObservable(User.class);
  }

  /*
   * This do the complete magic, make both network call
   * and then returns the list of user who loves both
   * Using zip operator to get both response at a time
   */
  private void findUsersWhoLovesBoth() {
    // here we are using zip operator to combine both request
    Observable.zip(getCricketFansObservable(), getFootballFansObservable(),
            new BiFunction<List<User>, List<User>, List<User>>() {
              @Override
              public List<User> apply(List<User> cricketFans, List<User> footballFans) {
                List<User> userWhoLovesBoth =
                        filterUserWhoLovesBoth(cricketFans, footballFans);
                return userWhoLovesBoth;
              }
            })
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Observer<List<User>>() {
              @Override
              public void onSubscribe(Disposable d) {

              }

              @Override
              public void onNext(List<User> users) {
                // do anything with user who loves both
                Log.d(TAG, "userList size : " + users.size());
                for (User user : users) {
                  Log.d(TAG, "user : " + user.toString());
                }
              }

              @Override
              public void onError(Throwable e) {
                Utils.logError(TAG, e);
              }

              @Override
              public void onComplete() {
                Log.d(TAG, "onComplete");
              }
            });
  }

  private List<User> filterUserWhoLovesBoth(List<User> cricketFans, List<User> footballFans) {
    List<User> userWhoLovesBoth = new ArrayList<>();

    for (User footballFan : footballFans) {
      if (cricketFans.contains(footballFan)) {
        userWhoLovesBoth.add(footballFan);
      }
    }

    return userWhoLovesBoth;
  }

  private Observable<List<User>> getAllMyFriendsObservable() {
    return Rx3AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllFriends/{userId}")
            .addPathParameter("userId", "1")
            .build()
            .getObjectListObservable(User.class);
  }

  private Observable<List<User>> getUserListObservable() {
    return Rx3AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAllUsers/{pageNumber}")
            .addPathParameter("pageNumber", "0")
            .addQueryParameter("limit", "10")
            .build()
            .getObjectListObservable(User.class);
  }

  private Observable<UserDetail> getUserDetailObservable(long id) {
    return Rx3AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUserDetail/{userId}")
            .addPathParameter("userId", String.valueOf(id))
            .build()
            .getObjectObservable(UserDetail.class);
  }


  public class ClickProxy {

    public void map() {
      Rx3AndroidNetworking.get("https://fierce-cove-29863.herokuapp.com/getAnUser/{userId}")
              .addPathParameter("userId", "1")
              .build()
              .getObjectObservable(ApiUser.class)
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .map(new Function<ApiUser, User>() {
                @Override
                public User apply(ApiUser apiUser) {
                  // here we get ApiUser from server
                  User user = new User(apiUser);
                  // then by converting, we are returning user
                  return user;
                }
              })
              .subscribe(new Observer<User>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(User user) {
                  Log.d(TAG, "user : " + user.toString());
                }

                @Override
                public void onError(Throwable e) {
                  Utils.logError(TAG, e);
                }

                @Override
                public void onComplete() {
                  Log.d(TAG, "onComplete");
                }
              });
    }

    public void zip() {
      findUsersWhoLovesBoth();
    }

    public void flatMapAndFilter() {
      getAllMyFriendsObservable()
              .flatMap(new Function<List<User>, ObservableSource<User>>() { // flatMap - to return users one by one
                @Override
                public ObservableSource<User> apply(List<User> usersList) {
                  return Observable.fromIterable(usersList); // returning user one by one from usersList.
                }
              })
              .filter(new Predicate<User>() {
                @Override
                public boolean test(User user) {
                  // filtering user who follows me.
                  return user.isFollowing;
                }
              })
              .subscribeOn(Schedulers.io())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Observer<User>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(User user) {
                  // only the user who is following me comes here one by one
                  Log.d(TAG, "user : " + user.toString());
                }

                @Override
                public void onError(Throwable e) {
                  Utils.logError(TAG, e);
                }

                @Override
                public void onComplete() {
                  Log.d(TAG, "onComplete");
                }
              });
    }

    public void take() {
      getUserListObservable()
              .flatMap(new Function<List<User>, ObservableSource<User>>() { // flatMap - to return users one by one
                @Override
                public ObservableSource<User> apply(List<User> usersList) {
                  return Observable.fromIterable(usersList); // returning user one by one from usersList.
                }
              })
              .take(4) // it will only emit first 4 users out of all
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Observer<User>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(User user) {
                  // // only four user comes here one by one
                  Log.d(TAG, "user : " + user.toString());
                }

                @Override
                public void onError(Throwable e) {
                  Utils.logError(TAG, e);
                }

                @Override
                public void onComplete() {
                  Log.d(TAG, "onComplete");
                }
              });
    }

    public void flatMap() {
      getUserListObservable()
              .flatMap(new Function<List<User>, ObservableSource<User>>() { // flatMap - to return users one by one
                @Override
                public ObservableSource<User> apply(List<User> usersList) {
                  return Observable.fromIterable(usersList); // returning user one by one from usersList.
                }
              })
              .flatMap(new Function<User, ObservableSource<UserDetail>>() {
                @Override
                public ObservableSource<UserDetail> apply(User user) {
                  // here we get the user one by one
                  // and returns corresponding getUserDetailObservable
                  // for that userId
                  return getUserDetailObservable(user.id);
                }
              })
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Observer<UserDetail>() {
                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onError(Throwable e) {
                  Utils.logError(TAG, e);
                }

                @Override
                public void onNext(UserDetail userDetail) {
                  // do anything with userDetail
                  Log.d(TAG, "userDetail : " + userDetail.toString());
                }

                @Override
                public void onComplete() {
                  Log.d(TAG, "onComplete");
                }
              });
    }

    public void flatMapWithZip() {
      getUserListObservable()
              .flatMap(new Function<List<User>, ObservableSource<User>>() { // flatMap - to return users one by one
                @Override
                public ObservableSource<User> apply(List<User> usersList) {
                  return Observable.fromIterable(usersList); // returning user one by one from usersList.
                }
              })
              .flatMap(new Function<User, ObservableSource<Pair<UserDetail, User>>>() {
                @Override
                public ObservableSource<Pair<UserDetail, User>> apply(User user) {
                  // here we get the user one by one and then we are zipping
                  // two observable - one getUserDetailObservable (network call to get userDetail)
                  // and another Observable.just(user) - just to emit user
                  return Observable.zip(getUserDetailObservable(user.id),
                          Observable.just(user),
                          new BiFunction<UserDetail, User, Pair<UserDetail, User>>() {
                            @Override
                            public Pair<UserDetail, User> apply(UserDetail userDetail, User user) {
                              // runs when network call completes
                              // we get here userDetail for the corresponding user
                              return new Pair<>(userDetail, user); // returning the pair(userDetail, user)
                            }
                          });
                }
              })
              .subscribeOn(Schedulers.newThread())
              .observeOn(AndroidSchedulers.mainThread())
              .subscribe(new Observer<Pair<UserDetail, User>>() {
                @Override
                public void onComplete() {
                  // do something onCompleted
                  Log.d(TAG, "onComplete");
                }

                @Override
                public void onError(Throwable e) {
                  // handle error
                  Utils.logError(TAG, e);
                }

                @Override
                public void onSubscribe(Disposable d) {

                }

                @Override
                public void onNext(Pair<UserDetail, User> pair) {
                  // here we are getting the userDetail for the corresponding user one by one
                  UserDetail userDetail = pair.first;
                  User user = pair.second;
                  Log.d(TAG, "user : " + user.toString());
                  Log.d(TAG, "userDetail : " + userDetail.toString());
                }
              });
    }
  }
}
