package com.mostafahelal.myapplication

import com.mostafahelal.myapplication.gym.data.remote.RemoteGym
import com.mostafahelal.myapplication.gym.domain.Gym

object DummyGymsList {
    fun getDummyGyms()= arrayListOf(
        RemoteGym(0,"n0","p0",false),
        RemoteGym(1,"n1","p1",false),
        RemoteGym(2,"n2","p2",false),
        RemoteGym(3,"n3","p3",false),
        RemoteGym(4,"n4","p4",false),
    )
    fun getDummyGeneralGyms()= arrayListOf(
        Gym(0,"n0","p0",false),
        Gym(1,"n1","p1",false),
        Gym(2,"n2","p2",false),
        Gym(3,"n3","p3",false),
        Gym(4,"n4","p4",false),
    )

}