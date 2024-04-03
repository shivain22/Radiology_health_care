"use client";
import React from "react";
import { FcPlus } from "react-icons/fc";
import {
  Card,
  CardContent,
  CardDescription,
  CardFooter,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Tabs, TabsContent, TabsList, TabsTrigger } from "@/components/ui/tabs";
import SignIn from "./SignIn";
import SignUp from "./signUp";

const AuthPage = () => {
  return (
    <>
      <div className="flex flex-col items-center pt-5 pb-10">
        <FcPlus className="text-6xl m-5" />
        <Tabs defaultValue="signin" className="w-full max-w-2xl px-5">
          <TabsList className="grid w-full grid-cols-2">
            <TabsTrigger value="signin">Sign in</TabsTrigger>
            <TabsTrigger value="signup">Sign Up</TabsTrigger>
          </TabsList>
          <TabsContent value="signin" className="sm:px-5">
            <Card className="w-full ">
              <CardHeader>
                <CardTitle className="text-2xl">Sign In </CardTitle>
                <CardDescription>
                  Enter your username and password below to login to your
                  account.
                </CardDescription>
              </CardHeader>
              <CardContent className="">
                <SignIn />
              </CardContent>
              <CardFooter />
            </Card>
          </TabsContent>
          <TabsContent value="signup" className="sm:px-5">
            <Card className="w-full ">
            <CardHeader>
                <CardTitle className="text-2xl">Sign Up </CardTitle>
                <CardDescription>
                  Please add the information for your new account.
                </CardDescription>
              </CardHeader>
              <CardContent className="">
                <SignUp />
              </CardContent>
              <CardFooter />
            </Card>
          </TabsContent>
        </Tabs>
      </div>
    </>
  );
};
export default AuthPage;
