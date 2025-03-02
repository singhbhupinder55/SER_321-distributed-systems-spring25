package example.grpcclient;

import io.grpc.stub.StreamObserver;
import service.FollowGrpc;
import service.FollowProto.*;
import service.UserReq;
import service.UserRes;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class FollowServiceImpl extends FollowGrpc.FollowImplBase {

    private final Map<String, Set<String>> userFollowers = new HashMap<>();

    @Override
    public void addUser(UserReq request, StreamObserver<UserRes> responseObserver) {
        String name = request.getName();
        if (userFollowers.containsKey(name)) {
            UserRes response = UserRes.newBuilder()
                    .setIsSuccess(false)
                    .setError("User already exists.")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        userFollowers.put(name, new HashSet<>());

        UserRes response = UserRes.newBuilder()
                .setIsSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void follow(UserReq request, StreamObserver<UserRes> responseObserver) {
        String name = request.getName();
        String followName = request.getFollowName();

        if (!userFollowers.containsKey(name)) {
            UserRes response = UserRes.newBuilder()
                    .setIsSuccess(false)
                    .setError("User not found. Register first.")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        if (!userFollowers.containsKey(followName)) {
            UserRes response = UserRes.newBuilder()
                    .setIsSuccess(false)
                    .setError("User to follow does not exist.")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        userFollowers.get(name).add(followName);

        UserRes response = UserRes.newBuilder()
                .setIsSuccess(true)
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }

    @Override
    public void viewFollowing(UserReq request, StreamObserver<UserRes> responseObserver) {
        String name = request.getName();

        if (!userFollowers.containsKey(name)) {
            UserRes response = UserRes.newBuilder()
                    .setIsSuccess(false)
                    .setError("User not found.")
                    .build();
            responseObserver.onNext(response);
            responseObserver.onCompleted();
            return;
        }

        UserRes response = UserRes.newBuilder()
                .setIsSuccess(true)
                .addAllFollowedUser(userFollowers.get(name))
                .build();
        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
