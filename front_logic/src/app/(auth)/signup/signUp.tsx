"use client";
import * as z from "zod";
import { Button } from "@/components/ui/button";
import {
  Form,
  FormControl,
  FormField,
  FormItem,
  FormLabel,
  FormMessage,
} from "@/components/ui/form";
import { Input } from "@/components/ui/input";
import { useForm } from "react-hook-form";
import { zodResolver } from "@hookform/resolvers/zod";
import { signUpForm } from "@/inferedTypes";
import { signUpSchema } from "@/formSchemas";
import { SignupUser } from "@/server_actions/(auth)/signup";


const SignUp= () => {
  const form = useForm<signUpForm>({
    resolver: zodResolver(signUpSchema),
    defaultValues: {
      username: "",
      firstname: "",
      lastname: "",
      email: "",
      password: "",
      confirmPassword: "",
    },
  });
  const handleSubmit=async(values:signUpForm)=>
  {
    try{
      await SignupUser(values);
    }
    catch(error)
    {
      console.error(error);
    }
  }
  return (
    <div className="flex flex-col  justify-between pt-5">
      <Form {...form}>
        <form className="max-w-md w-full flex flex-col gap-4" onSubmit={form.handleSubmit(handleSubmit)}>
          <FormField
            control={form.control}
            name="username"
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>Username</FormLabel>
                  <FormControl>
                    <Input placeholder="username" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              );
            }}
          />

          <FormField
            control={form.control}
            name="firstname"
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>First Name:</FormLabel>
                  <FormControl>
                    <Input placeholder="first name" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              );
            }}
          />

          <FormField
            control={form.control}
            name="lastname"
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>Last Name:</FormLabel>
                  <FormControl>
                    <Input placeholder="last name" {...field} />
                  </FormControl>
                </FormItem>
              );
            }}
          />

          <FormField
            control={form.control}
            name="email"
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>Email:</FormLabel>
                  <FormControl>
                    <Input placeholder="email" type="email" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              );
            }}
          />
          <FormField
            control={form.control}
            name="password"
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>Password:</FormLabel>
                  <FormControl>
                    <Input placeholder="password" type="password" {...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              );
            }}
          />
          <FormField
            control={form.control}
            name="confirmPassword"
            render={({ field }) => {
              return (
                <FormItem>
                  <FormLabel>Confirm Password:</FormLabel>
                  <FormControl>
                    <Input placeholder="confirm password" type="password"{...field} />
                  </FormControl>
                  <FormMessage />
                </FormItem>
              );
            }}
          />
          <Button className="w-full">Submit</Button>
        </form>
      </Form>
    </div>
  );
};
export default SignUp;
